package com.yxhuang.gintoinc.internal;

import android.util.Log;

/**
 * Created by yxhuang
 * Date: 2019/5/23
 * Description:
 */
public class DebugLog {

    private DebugLog() {
    }

    public static void log(String tag, String message){
        Log.d(tag, message);
    }
}
