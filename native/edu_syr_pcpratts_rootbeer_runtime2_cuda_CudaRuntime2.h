/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2 */

#ifndef _Included_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
#define _Included_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    findReserveMem
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_findReserveMem
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    setup
 * Signature: (IIJ)V
 */
JNIEXPORT void JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_setup
  (JNIEnv *, jobject, jint, jint, jlong);

/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    printDeviceInfo
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_printDeviceInfo
  (JNIEnv *, jclass);

/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    loadFunction
 * Signature: (JLjava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_loadFunction
  (JNIEnv *, jobject, jlong, jstring, jint);

/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    writeClassTypeRef
 * Signature: ([I)V
 */
JNIEXPORT void JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_writeClassTypeRef
  (JNIEnv *, jobject, jintArray);

/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    runBlocks
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_runBlocks
  (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    unload
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_unload
  (JNIEnv *, jobject);

/*
 * Class:     edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2
 * Method:    reinit
 * Signature: (IIJ)V
 */
JNIEXPORT void JNICALL Java_edu_syr_pcpratts_rootbeer_runtime2_cuda_CudaRuntime2_reinit
  (JNIEnv *, jobject, jint, jint, jlong);

#ifdef __cplusplus
}
#endif
#endif
