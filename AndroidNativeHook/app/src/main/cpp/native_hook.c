//
// Created by yxhuang on 2021/5/3.
//
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <EGL/egl.h>
#include <GLES/gl.h>
#include <elf.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <jni.h>
#include "native_hook.h"
#include "logger.h"

#include <android/log.h>
#include <inttypes.h>
#include <dlfcn.h>
#include <jni.h>


#define PAGE_START(address) ((address) & PAGE_MASK)
#define PAGE_END(address)   (PAGE_START(address) + PAGE_SIZE)

size_t (*old_fwrite)(const void *buf, size_t size, size_t count, FILE *fp);

size_t new_fwrite(const void *buf, size_t size, size_t count, FILE *fp) {
    // 插入文本
    const char *text = "hello native ";
    LOG_D("new_fwrite hook fwrite success insert text: %s", text);
    old_fwrite(text, strlen(text), 1, fp);
    return old_fwrite(buf, size, count, fp);
}

/**
 *  获取so的加载基址
 */
void *get_module_base(pid_t pid, const char *module_name) {
    FILE *fp;
    long addr = 0;
    char filename[32] = "\n";
    char line[1024] = "\n";
    LOG_D("get_module_base pid=%d", pid);
    if(pid < 0){
        snprintf(filename, sizeof(filename), "/proc/self/maps");
    } else {
        snprintf(filename, sizeof(filename), "/proc/%d/maps", pid);
    }
    // 获取指定 pid 进程加载的内存模块信息
    fp = fopen(filename, "r");
    while (fgets(line, sizeof(line), fp)) {
        if (NULL != strstr(line, module_name) &&
            sscanf(line, "%"PRIxPTR"-%*lx %*4s 00000000", &addr) == 1)
            break;
    }
    fclose(fp);
    return (void *) addr;
}


int hook_fwrite(const char *soPath, const char *hookFuncName) {
    // 1. 获取目标进程中模块的加载地址
    void *base_addr = get_module_base(getpid(), soPath);
    LOG_D("hook_fwrite base address = 0x%08X", base_addr);
    // 2.保存被 hook 的目标函数的原始调用地址
    old_fwrite = fwrite;
    LOG_D("origin fopen = 0x%08X", old_fwrite);
    // 3.计算 header table 的实际地址
    Elf32_Ehdr *header = (Elf32_Ehdr *) (base_addr);
    LOG_D("hook_fwrite e_ident = %d", header->e_ident[0]);
    // 通过 ELF 头文件的 e_ident 的 magic 对比，判断是不是 ELF
    if (memcmp(header->e_ident, "\177ELF", 4) != 0) {
        LOG_D("hook_fwrite is not so file return 0");
        return 0;
    }
    // 程序头表地址 program header = base_addr + 程序头表在Elf文件中的偏移
    Elf32_Phdr *phdr_table = (Elf32_Phdr *) (base_addr + header->e_phoff);
    if (phdr_table == 0) {
        LOG_D("hook_fwrite phdr_table address : 0");
        return 0;
    }
    // 程序头表里面 Segment 的数量
    size_t phdr_count = header->e_phnum;
    LOG_D("hook_fwrite phdr_count : %d", phdr_count);
    // 遍历程序头表，找到 dynamic 段
    unsigned long p_vaddr = 0;  // dynamic 段在虚拟内存的地址
    unsigned int p_memsz = 0;  // dynamic 段在虚拟内存的中所占用的长度
    int j = 0;
    for (j = 0; j < phdr_count; j++) {
        if (phdr_table[j].p_type == PT_DYNAMIC) {
            // 打印 .dynamic 段的虚拟内存地址
            LOG_D("hook_fwrite phdr_table[j].p_vaddr : %x", phdr_table[j].p_vaddr);
            p_vaddr = base_addr + phdr_table[j].p_vaddr;
            p_memsz = phdr_table[j].p_memsz;
            break;
        }
    }
    LOG_D("hook_fwrite p_vaddr: %x", p_vaddr);
    LOG_D("hook_fwrite p_memsz: %x", p_memsz);

    // .dynamic　段的结构  /*
    //    typedef struct dynamic {
    //         Elf32_Sword d_tag;
    //         union {
    //          Elf32_Sword d_val;
    //          Elf32_Addr d_ptr;
    //          } d_un;
    //   } Elf32_Dyn;
    Elf32_Dyn *dynamic_table = (Elf32_Dyn *) p_vaddr;
    unsigned long jmpRelOff = 0;
    unsigned long strTabOff = 0;
    unsigned long ptlRelSz = 0;
    unsigned long symTabOff = 0;

    // 多少个 .dynamic 段  (理解这段代码)
    // 遍历 动态段
    int dynCount = p_memsz / sizeof(Elf32_Dyn);
    for (int i = 0; i < dynCount; i++) {
        int val = dynamic_table[i].d_un.d_val;
        int d_tag = dynamic_table[i].d_tag;
        switch (d_tag) {
            case DT_JMPREL:  // Address of relocations associated with PLT PTL 的重定位表地址
                jmpRelOff = val;
                break;
            case DT_STRTAB:
                strTabOff = val; // Address of dynamic string table,动态链接字符串表地址
                break;
            case DT_PLTRELSZ:  // Size of relocation entries in PLT, PLT 中的所有重新定位项大小
                ptlRelSz = val / sizeof(Elf32_Rel);
                break;
            case DT_SYMTAB: // Address of dynamic symbol table, 动态链接符号表的地址
                symTabOff = val;
                break;
        }
    }

    // 重定位表
    /**
     * typefef struct{
     *    Elf32_Addr r_offset;
     *    Elf32_Word r_info;
     * } Elf32_Rel;
     *
     * typedef struct elf32_sym {
     *     Elf32_Word st_name;
     *     Elf32_Addr st_value;
      *    Elf32_Word st_size;
      *    unsigned char st_info;
     *     unsigned char st_other;
      *    Elf32_Half st_shndx;
     *  } Elf32_Sym;
     */
    Elf32_Rel *rel_table = (Elf32_Rel *) (jmpRelOff + base_addr);
    LOG_D("jmpRelOff : %x", jmpRelOff);
    LOG_D("strTabOff : %x", strTabOff);
    LOG_D("symTabOff : %x", symTabOff);
    // 遍历 got 表
    for (int i = 0; i < ptlRelSz; i++) {
        //ELF32_R_SYM 右移八位，查找改符号（rel_table[i].r_info）在 rel_table 符号表中下标
        uint16_t ndx = ELF32_R_SYM(rel_table[i].r_info);
        // 符号表  地址 = 基址 + got 表地址 + 下标 * 符号大小
        Elf32_Sym *symTable = (base_addr + symTabOff + ndx * sizeof(Elf32_Sym));
        // 获取符号名 ???
        char *funcName = (char *) (symTable->st_name + base_addr + strTabOff);
        // 找到fwrite 函数
        if (memcmp(funcName, hookFuncName, strlen(funcName)) == 0) {
            // 获取当前内存分页的大小
            uint32_t page_size = getpagesize();
            // 获取内存分页的起始地址（需要内存对齐）
            uint32_t mem_page_start = base_addr + rel_table[i].r_offset;
            LOG_D("hook_fwrite old_function=0x%08X new_function=0x%08X", mem_page_start,
                  new_fwrite);
            LOG_D("hook_fwrite mem_page_start=0x%08X page_size=%d", mem_page_start, page_size);
            // 修改内存属性为可写
            mprotect((uint32_t) PAGE_START(mem_page_start), page_size,
                     PROT_READ | PROT_WRITE | PROT_EXEC);
            // 替换
            *(unsigned int *) (base_addr + rel_table[i].r_offset) = new_fwrite;
            // 清除指令缓存
            __builtin___clear_cache((void *) PAGE_START(mem_page_start),
                                    (void *) PAGE_END(mem_page_start));
        }
    }
    return 0;
}


JNIEXPORT void JNICALL
Java_com_yxhuang_nativehook_NativeHook_hookWrite(JNIEnv *env, jobject thiz,
        jstring so_path, jstring hook_func) {
    const char *soPath = (*env)->GetStringUTFChars(env, so_path, 0);
    const char *hookFuncName = (*env)->GetStringUTFChars(env, hook_func, 0);
    LOG_D("hook soPath = %s  hook_func = %s", soPath, hookFuncName);
    hook_fwrite(soPath, hookFuncName);
}