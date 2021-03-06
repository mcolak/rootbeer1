/* 
 * Copyright 2012 Phil Pratt-Szeliga and other contributors
 * http://chirrup.org/
 * 
 * See the file LICENSE for copying permission.
 */

package edu.syr.pcpratts.rootbeer.test;

import edu.syr.pcpratts.rootbeer.configuration.Configuration;
import edu.syr.pcpratts.rootbeer.runtime.Kernel;
import edu.syr.pcpratts.rootbeer.runtime.Rootbeer;
import edu.syr.pcpratts.rootbeer.runtime.util.Stopwatch;
import edu.syr.pcpratts.rootbeer.test.LoadTestSerialization;
import edu.syr.pcpratts.rootbeer.test.TestException;
import edu.syr.pcpratts.rootbeer.test.TestSerialization;
import edu.syr.pcpratts.rootbeer.util.ForceGC;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RootbeerTestAgent {

  private long m_cpuTime;
  private long m_gpuTime;
  private boolean m_passed;
  private String m_message;
  private List<String> m_failedTests;
  
  public RootbeerTestAgent(){
    m_failedTests = new ArrayList<String>();
  }
  
  public void testOne(ClassLoader cls_loader, String test_case) throws Exception {
    Class test_case_cls = cls_loader.loadClass(test_case);
    Object test_case_obj = test_case_cls.newInstance();
    if(test_case_obj instanceof TestSerialization){
      TestSerialization test_ser = (TestSerialization) test_case_obj;
      System.out.println("[TEST 1/1] "+test_ser.toString());
      test(test_ser, true);      
      if(m_passed){
        System.out.println("  PASSED");
        System.out.println("  Cpu time: "+m_cpuTime+" ms");
        System.out.println("  Gpu time: "+m_gpuTime+" ms");
      } else {
        System.out.println("  FAILED");
        System.out.println("  "+m_message);
      }   
    } else if(test_case_obj instanceof TestException){
      TestException test_ex = (TestException) test_case_obj;
      System.out.println("[TEST 1/1] "+test_ex.toString());
      ex_test(test_ex, true);
      if(m_passed){
        System.out.println("  PASSED");
      } else {
        System.out.println("  FAILED");
        System.out.println("  "+m_message);
      }        
    } else {
      throw new RuntimeException("unknown test case type");
    }
  }
  
  public void test(ClassLoader cls_loader, boolean run_hard_tests) throws Exception {
    LoadTestSerialization loader = new LoadTestSerialization();
    List<TestSerialization> creators = loader.load(cls_loader, "edu.syr.pcpratts.rootbeer.test.Main", run_hard_tests);
    List<TestException> ex_creators = loader.loadException(cls_loader, "edu.syr.pcpratts.rootbeer.test.ExMain");
    int num_tests = creators.size() + ex_creators.size();
    int test_num = 1;

    for(TestSerialization creator : creators){
      System.out.println("[TEST "+test_num+"/"+num_tests+"] "+creator.toString());
      test(creator, false);
      ForceGC.gc();
      if(m_passed){
        System.out.println("  PASSED");
        System.out.println("  Cpu time: "+m_cpuTime+" ms");
        System.out.println("  Gpu time: "+m_gpuTime+" ms");
      } else {
        System.out.println("  FAILED");
        System.out.println("  "+m_message);
        m_failedTests.add(creator.toString());
      }        
      ++test_num;
    }

    for(TestException ex_creator : ex_creators){
      System.out.println("[TEST "+test_num+"/"+num_tests+"] "+ex_creator.toString());
      ex_test(ex_creator, false);
      if(m_passed){
        System.out.println("  PASSED");
      } else {
        System.out.println("  FAILED");
        System.out.println("  "+m_message);
        m_failedTests.add(ex_creator.toString());
      }        
      ++test_num;
    }

    int test_passed = num_tests - m_failedTests.size();
    System.out.println(test_passed+"/"+num_tests+" tests PASS");
    if(test_passed == num_tests){
      System.out.println("ALL TESTS PASS!");
    } else {
      System.out.println("Failing tests:");
      for(String failure : m_failedTests){
        System.out.println("  "+failure);
      }
    } 
  }
  
  private void test(TestSerialization creator, boolean print_mem) {
    int i = 0;
    try {      
      Rootbeer rootbeer = new Rootbeer();
      Configuration.setPrintMem(print_mem);
      List<Kernel> known_good_items = creator.create();
      List<Kernel> testing_items = creator.create();
      Stopwatch watch = new Stopwatch();
      watch.start();
      rootbeer.runAll(testing_items);
      if(rootbeer.getRanGpu() == false){
        m_message = "Ran on CPU";
        m_passed = false;
        return;
      }
      m_passed = true;
      watch.stop();
      m_gpuTime = watch.elapsedTimeMillis();
      watch.start();
      for(i = 0; i < known_good_items.size(); ++i){       
        Kernel known_good_item = known_good_items.get(i);
        known_good_item.gpuMethod();
      }
      watch.stop();
      m_cpuTime = watch.elapsedTimeMillis();
      for(i = 0; i < known_good_items.size(); ++i){
        Kernel known_good_item = known_good_items.get(i);
        Kernel testing_item = testing_items.get(i);
        if(!creator.compare(known_good_item, testing_item)){
          m_message = "Compare failed at: "+i;
          m_passed = false;
          return;
        }
      }
    } catch(Throwable ex){
      ex.printStackTrace(System.out);
      m_message = "Exception thrown at index: "+i+"\n";
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      PrintWriter writer = new PrintWriter(os);
      ex.printStackTrace(writer);
      writer.flush();
      writer.close();
      m_message += os.toString();
      m_passed = false;
    }
  }

  private void ex_test(TestException creator, boolean print_mem) {
    Rootbeer rootbeer = new Rootbeer();
    Configuration.setPrintMem(print_mem);
    List<Kernel> testing_items = creator.create();
    try {
      rootbeer.runAll(testing_items);
      if(rootbeer.getRanGpu() == false){
        m_message = "Ran on CPU";
        m_passed = false;
        return;
      }
      m_passed = false;
      m_message = "No exception thrown when expecting one.";
    } catch(Throwable ex){
      if(rootbeer.getRanGpu() == false){
        m_message = "Ran on CPU";
        m_passed = false;
        return;
      }
      m_passed = creator.catchException(ex);
      if(m_passed == false){
        m_message = "Exception is: "+ex.toString(); 
      }
    }
  }
}
