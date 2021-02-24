package com.yxhuang.androiddailydemo.poll;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
public class MonitorThread {

    private static final String TAG = "MonitorThread";

    private Handler handler;
    private IPollMonitor monitor;

    public MonitorThread() {
        HandlerThread thread = new HandlerThread("MonitorThread");
        thread.start();
        handler = new Handler(thread.getLooper());
    }

    public void start(IPollMonitor monitor){
        Log.i(TAG, "start");
        isPolling = true;
        this.monitor = monitor;
        this.monitor.run();
        handler.postDelayed(new MonitorRunnable(monitor), monitor.pollInterval());
    }


    public void resume(){
        Log.i(TAG, "resume");
        if (!isPolling){
            isPolling = true;
            monitor.run();
            handler.postDelayed(new MonitorRunnable(monitor), monitor.pollInterval());
        }
    }

    public void pause(){
        Log.i(TAG, "pause");
        isPolling = false;
        monitor.stop();
        handler.removeCallbacksAndMessages(null);
    }

    public void stop(){
        isPolling = false;
    }

    private volatile boolean isPolling = false;

    class MonitorRunnable implements Runnable{

        private IPollMonitor mMonitor;

        public MonitorRunnable(IPollMonitor monitor) {
            mMonitor = monitor;
        }

        @Override
        public void run() {
            if (isPolling){
                mMonitor.run();
                handler.postDelayed(this, mMonitor.pollInterval());
            }
        }
    }
}
