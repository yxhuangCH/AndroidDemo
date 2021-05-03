#include <jni.h>

//
// Created by yxhuang on 2021/5/3.
//

#ifndef ANDROIDNATIVEHOOK_NATIVE_HOOK_H
#define ANDROIDNATIVEHOOK_NATIVE_HOOK_H


#ifdef __cplusplus
extern "C" {
#endif


JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_hookWrite(JNIEnv* env, jobject thiz,
        jstring so_path, jstring hook_func);

#ifdef __cplusplus
}
#endif


#endif //ANDROIDNATIVEHOOK_NATIVE_HOOK_H
