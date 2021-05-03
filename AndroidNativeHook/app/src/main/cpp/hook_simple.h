#include <jni.h>

//
// Created by yxhuang on 2021/5/2.
//

#ifndef ANDROIDNATIVEHOOK_HOOK_SIMPLE_H
#define ANDROIDNATIVEHOOK_HOOK_SIMPLE_H


#ifdef __cplusplus
extern "C" {
#endif


JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_hookSimple(JNIEnv* env,jobject thiz, jstring so_name);

#ifdef __cplusplus
}
#endif


#endif //ANDROIDNATIVEHOOK_HOOK_SIMPLE_H
