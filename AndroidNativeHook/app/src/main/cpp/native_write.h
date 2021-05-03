//
// Created by yxhuang on 2021/5/2.
//
#include "../../../../../../../../../software/android/sdk/ndk/21.0.6113669/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"

#ifndef ANDROIDNATIVEHOOK_NATIVE_WRITE_H
#define ANDROIDNATIVEHOOK_NATIVE_WRITE_H


#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_writeText(JNIEnv *env, jobject obj, jstring filePath,
jstring jText);

#ifdef __cplusplus
}
#endif


#endif //ANDROIDNATIVEHOOK_NATIVE_WRITE_H
