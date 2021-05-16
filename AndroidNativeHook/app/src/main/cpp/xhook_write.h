#include <jni.h>
#include "../../../../../../../../../software/android/sdk/ndk/21.0.6113669/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"
//
// Created by yxhuang on 2021/5/4.
//

#ifndef ANDROIDNATIVEHOOK_XHOOK_WRITE_H
#define ANDROIDNATIVEHOOK_XHOOK_WRITE_H


#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_xhookInit(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_xhookWrite(JNIEnv *env,jobject thiz, jstring hook_content);
#ifdef __cplusplus
}
#endif


#endif //ANDROIDNATIVEHOOK_XHOOK_WRITE_H
