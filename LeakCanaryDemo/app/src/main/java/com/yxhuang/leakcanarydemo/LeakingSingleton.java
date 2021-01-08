package com.yxhuang.leakcanarydemo;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxhuang
 * Date: 2020/10/25
 * Description:
 */
public class LeakingSingleton {

    private static volatile LeakingSingleton sInstance;

    public static LeakingSingleton getInstance(){
        if (sInstance == null){
            synchronized (LeakingSingleton.class){
                if (sInstance == null){
                    sInstance = new LeakingSingleton();
                }
            }
        }
        return sInstance;
    }

    private LeakingSingleton(){

    }

    List<View> leakedViews = new ArrayList<>();


}
