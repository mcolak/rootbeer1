/* 
 * Copyright 2012 Phil Pratt-Szeliga and other contributors
 * http://chirrup.org/
 * 
 * See the file LICENSE for copying permission.
 */

package edu.syr.pcpratts.rootbeer.runtime;

import java.util.ArrayList;
import java.util.List;

public class RootbeerGpu {

  private static boolean m_isOnGpu;
  private static byte[] m_sharedMem;
  
  static {
    m_isOnGpu = false;
    m_sharedMem = new byte[48*1024];
  }
  
  public static boolean isOnGpu(){
    return m_isOnGpu;
  }
  
  public static void setIsOnGpu(boolean value){
    m_isOnGpu = value;
  }

  public static int getThreadId() {
    return 0;
  }
  
  public static void synchthreads(){ 
  }

  public static long getRef(Object obj) {
    return 0;
  }

  public static byte getSharedByte(int index){
    return m_sharedMem[index];
  }
  
  public static void setSharedByte(int index, byte value){
    m_sharedMem[index] = value;
  }
  
  public static char getSharedChar(int index){
    char ret = 0;
    ret |= m_sharedMem[index] & 0xff;
    ret |= (m_sharedMem[index + 1] << 8) & 0xff00;
    return ret;
  }
  
  public static void setSharedChar(int index, char value){ 
    m_sharedMem[index] = (byte) (value & 0xff);
    m_sharedMem[index + 1] = (byte) ((value >> 8) & 0xff);
  }
  
  public static boolean getSharedBoolean(int index){
    if(m_sharedMem[index] == 1){
      return true;
    } else {
      return false;
    }
  }
  
  public static void setSharedBoolean(int index, boolean value){
    byte value_byte;
    if(value == true){
      value_byte = 1;
    } else {
      value_byte = 0; 
    }
    m_sharedMem[index] = value_byte;
  }
  
  public static short getSharedShort(int index){
    short ret = 0;
    ret |= m_sharedMem[index] & 0xff;
    ret |= (m_sharedMem[index + 1] << 8) & 0xff00;
    return ret;
  }
  
  public static void setSharedShort(int index, short value){
    m_sharedMem[index] = (byte) (value & 0xff);
    m_sharedMem[index + 1] = (byte) ((value >> 8) & 0xff);
  }
  
  public static int getSharedInteger(int index){
    int ret = 0;
    ret |= m_sharedMem[index] & 0x000000ff;
    ret |= (m_sharedMem[index + 1] <<  8) & 0x0000ff00;
    ret |= (m_sharedMem[index + 2] << 16) & 0x00ff0000;
    ret |= (m_sharedMem[index + 3] << 24) & 0xff000000;
    return ret;  
  }
  
  public static void setSharedInteger(int index, int value){
    m_sharedMem[index] = (byte) (value & 0xff);
    m_sharedMem[index + 1] = (byte) ((value >> 8)  & 0xff);
    m_sharedMem[index + 2] = (byte) ((value >> 16) & 0xff);
    m_sharedMem[index + 3] = (byte) ((value >> 24) & 0xff);
  }
  
  public static long getSharedLong(int index){
    long ret = 0;
    ret |=  (long) m_sharedMem[index]            & 0x00000000000000ffL;
    ret |= ((long) m_sharedMem[index + 1] <<  8) & 0x000000000000ff00L;
    ret |= ((long) m_sharedMem[index + 2] << 16) & 0x0000000000ff0000L;
    ret |= ((long) m_sharedMem[index + 3] << 24) & 0x00000000ff000000L;
    ret |= ((long) m_sharedMem[index + 4] << 32) & 0x000000ff00000000L;
    ret |= ((long) m_sharedMem[index + 5] << 40) & 0x0000ff0000000000L;
    ret |= ((long) m_sharedMem[index + 6] << 48) & 0x00ff000000000000L;
    ret |= ((long) m_sharedMem[index + 7] << 56) & 0xff00000000000000L;
    return ret;    
  }
  
  public static void setSharedLong(int index, long value){
    m_sharedMem[index] = (byte) (value & 0xff);
    m_sharedMem[index + 1] = (byte) ((value >> 8)  & 0xff);
    m_sharedMem[index + 2] = (byte) ((value >> 16) & 0xff);
    m_sharedMem[index + 3] = (byte) ((value >> 24) & 0xff);
    m_sharedMem[index + 4] = (byte) ((value >> 32) & 0xff);
    m_sharedMem[index + 5] = (byte) ((value >> 40) & 0xff);
    m_sharedMem[index + 6] = (byte) ((value >> 48) & 0xff);
    m_sharedMem[index + 7] = (byte) ((value >> 56) & 0xff);
  }
  
  public static float getSharedFloat(int index){
    int value_int = getSharedInteger(index);
    return Float.intBitsToFloat(value_int);
  }
  
  public static void setSharedFloat(int index, float value){ 
    int value_int = Float.floatToIntBits(value);
    setSharedInteger(index, value_int);
  }
  
  public static double getSharedDouble(int index){
    long value_long = getSharedLong(index);
    return Double.longBitsToDouble(value_long);
  }
  
  public static void setSharedDouble(int index, double value){
    long value_long = Double.doubleToLongBits(value);
    setSharedLong(index, value_long);
  }
  
  
}
