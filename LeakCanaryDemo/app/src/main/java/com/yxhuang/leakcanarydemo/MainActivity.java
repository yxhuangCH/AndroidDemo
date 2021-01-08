package com.yxhuang.leakcanarydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExampleApplication app = (ExampleApplication) getApplication();
        TextView leakedView = findViewById(R.id.helper_text);
        findViewById(R.id.recreate_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        final PostDelay postDelay = new PostDelay();
        findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDelay.startPost();
            }
        });

//        int rand = new Random().nextInt(4);
//        switch (rand) {
//            case 0:
//                app.leakedViews.add(leakedView);
//                break;
//
//            case 1:
//                LeakingSingleton.getInstance().leakedViews.add(leakedView);
//                break;
//
//            case 2:
//                final AtomicReference<Activity> ref = new AtomicReference<Activity>(this);
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Activity activity = ref.get();
//                        while (true) {
//                            Log.i(TAG, "activity " + activity);
//                            SystemClock.sleep(1000);
//                        }
//                    }
//                });
//                thread.setName("Leaking local variables");
//                thread.start();
//
//                break;
//
//            default:
//                LeakingThread.leakedViews.add(leakedView);
//                break;
//        }
    }

    // 首先定义一个HashMap，保存软引用对象。
    private Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
    // 再来定义一个方法，保存Bitmap的软引用到HashMap。
    public void addBitmapToCache(String path) {
        // 强引用的Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        // 软引用的Bitmap对象
        SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
        // 添加该对象到Map中使其缓存
        imageCache.put(path, softBitmap);
    }
    // 获取的时候，可以通过SoftReference的get()方法得到Bitmap对象。
    public Bitmap getBitmapByPath(String path) {
        // 从缓存中取软引用的Bitmap对象
        SoftReference<Bitmap> softBitmap = imageCache.get(path);
        // 判断是否存在软引用
        if (softBitmap == null) {
            return null;
        }
        // 通过软引用取出Bitmap对象，如果由于内存不足Bitmap被回收，将取得空 ，如果未被回收，则可重复使用，提高速度。
        Bitmap bitmap = softBitmap.get();
        return bitmap;
    }
}