//
// Created by yxhuang on 2021/5/4.
//
#include <stdio.h>
#include <string.h>
#include <dlfcn.h>
#include "xhook_write.h"
#include "xhook.h"
#include "logger.h"

void (*writeText)(const char *path, const char *text) = NULL;


void *xhook_write(const char *path, const char *text){
    // 插入文本
    const char *textHook = "hello native ";
    LOG_D("new_fwrite hook fwrite success insert text: %s", text);
    writeText(path, textHook);
}

JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_xhookInit(JNIEnv *env, jobject thiz){
    LOG_D("xhookInit init");
//    void *libwirte = dlopen("libnative-write.so", RTLD_NOW);
//    if (!libwirte){
//        LOG_D("xhookInit open libnative-write.so fail ");
//        return;
//    }
//    writeText = dlsym(libwirte, "writeText");
//    LOG_D("xhookInit old_write %d", writeText != NULL);
}


JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_xhookWrite(JNIEnv *env,jobject thiz, jstring hook_content){
    const char *hookContent = (*env)->GetStringChars(env, hook_content, 0);
    LOG_D("xhookWrite hook_content=%s", hookContent);

    xhook_register("libnative-write.so", "writeText",
            (void *)xhook_write, NULL);

    xhook_refresh(0);
    xhook_clear();
}
