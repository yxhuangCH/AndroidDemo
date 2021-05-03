package com.yxhuang.nativehook;

/**
 * Created by yxhuang
 * Date: 2021/5/2
 * Description:
 */
public class NativeHook {

    static {
        System.loadLibrary("native-write");
        System.loadLibrary("hook-simple");
        System.loadLibrary("native-hook");
    }

    /**
     *  写入文件内容
     * @param path      文件路径 /Android/data/com.yxhuang.androidnativehook/open.txt
     * @param content   写入内容
     */
    public native void writeText(String path, String content);

    /**
     *  简单的写死偏移地址
     * @param soName
     */
    public native void hookSimple(String soName);

    public native void hookWrite(String soPath, String hookFunc);
}
