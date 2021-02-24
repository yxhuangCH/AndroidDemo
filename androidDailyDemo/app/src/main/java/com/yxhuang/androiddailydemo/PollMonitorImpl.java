package com.yxhuang.androiddailydemo;

import android.util.Log;

import com.yxhuang.androiddailydemo.poll.IPollMonitor;

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
public class PollMonitorImpl implements IPollMonitor {

    private static final String TAG = "PollMonitorImpl";

    @Override
    public void run() {
        Log.i(TAG, "run");
    }

    @Override
    public void stop() {
        Log.i(TAG, "stop");
    }

    @Override
    public int pollInterval() {
        return 2 * 1000;
    }

}
