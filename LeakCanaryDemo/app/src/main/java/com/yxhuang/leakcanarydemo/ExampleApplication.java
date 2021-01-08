package com.yxhuang.leakcanarydemo;

import android.app.Application;
import android.os.StrictMode;
import android.view.View;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxhuang
 * Date: 2020/10/25
 * Description:
 */
public class ExampleApplication extends Application {
     List<View> leakedViews = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
//        enabledStrictMode();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .penaltyDeath()
                        .build()
        );
    }
}
