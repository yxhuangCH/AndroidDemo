//
// Created by yxhuang on 2021/5/2.
//

#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <inttypes.h>
#include <sys/mman.h>
#include <sys/user.h>
#include "hook_simple.h"
#include "logger.h"


#define PAGE_START(addr) ((addr) & PAGE_MASK)
#define PAGE_END(addr)(PAGE_START(addr) + PAGE_SIZE)

size_t (*old_fwrite)(const void *buf, size_t size, size_t count, FILE *fp);


size_t hook_fwrite(const void *buf, size_t size, size_t count, FILE *fp){
    LOG_D("hook simple fwrite success");
    // 这里插入要 hook 的内容
    const char *text = "hello native ";
    old_fwrite(text, strlen(text), 1, fp);
    return old_fwrite(buf, size, count, fp);
}

JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_hookSimple(JNIEnv* env,jobject thiz, jstring so_name){
    const char *soName = (*env)->GetStringUTFChars(env, so_name, 0);
    LOG_D("so name =%s", soName);

    char line[1024] = "\n";
    FILE *fp = NULL;
    uintptr_t base_addr = 0;
    uintptr_t addr = 0;

    // 查找自身对应的基址
    if(NULL == (fp = fopen("/proc/self/maps", "r"))) return;
    while (fgets(line, sizeof(line), fp)) {
        if (NULL != strstr(line, soName) &&
            sscanf(line, "%" PRIxPTR"-%*lx %*4s 00000000", &base_addr) == 1)
            break;
    }
    fclose(fp);
    LOG_D("base address=0x%08X", base_addr);
    if(0 == base_addr){
        return;
    }
    // 函数地址 = 基址 + 偏移地址
    addr = base_addr + 0x1fd8;
    LOG_D("value=0x%08X address=0x%08X fwrite=0x%08X", addr, *(uintptr_t *) addr, fwrite);

    //调整写权限
    mprotect((void *) PAGE_START(addr), PAGE_SIZE, PROT_READ | PROT_WRITE);

    //保存旧地址
    old_fwrite = *(void **) addr;

    //替换新的目标地址
    *(void **) addr = hook_fwrite;

    //清除指令缓存
    __builtin___clear_cache((void *) PAGE_START(addr), (void *) PAGE_END(addr));
}
