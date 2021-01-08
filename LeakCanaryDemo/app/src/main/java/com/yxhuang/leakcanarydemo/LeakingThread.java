package com.yxhuang.leakcanarydemo;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxhuang
 * Date: 2020/10/25
 * Description:
 */
public class LeakingThread extends Thread {

    private Object obj = new Object();
    static LeakingThread thread = new LeakingThread();
    static List<View> leakedViews = new ArrayList<>();

    public LeakingThread() {
        setName("Leaking thread");
        start();
    }

    @Override
    public void run() {
        synchronized (obj){
            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
