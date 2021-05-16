package com.yxhuang.nativehook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.io.File;

import dalvik.system.PathClassLoader;

/**
 * Created by yxhuang
 * Date: 2021/4/28
 * Description:
 */
public class JNIActivity extends Activity {


    private NativeHook mNativeHook = new NativeHook();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);


        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File textFile = new File(getExternalFilesDir(null), "open.txt");
                mNativeHook.writeText(textFile.getAbsolutePath(), "world");
            }
        });

        findViewById(R.id.btn_simple_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNativeHook.hookSimple("hook-simple");
            }
        });

        findViewById(R.id.btn_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PathClassLoader pathClassLoader = (PathClassLoader) getClassLoader();
                String nativeWritePath = pathClassLoader.findLibrary("native-write");
                mNativeHook.hookWrite(nativeWritePath, "fwrite");
            }
        });
        findViewById(R.id.btn_xhook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNativeHook.xhookWrite("xhook");
            }
        });
    }


}
