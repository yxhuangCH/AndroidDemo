package com.yxhuang.leakcanarydemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import com.squareup.leakcanary.Retryable;

/**
 * Created by yxhuang
 * Date: 2020/11/12
 * Description:
 */
public class PostDelay {

    static final String POST_THREAD_NAME = "post_thread";

    private Handler backgroundHandler;
    private Handler mainHandler;
    private final long initialDelayMillis = 1000L;
    private final long maxBackoffFactor;

    public PostDelay() {
        mainHandler = new Handler(Looper.getMainLooper());
        HandlerThread handlerThread = new HandlerThread(POST_THREAD_NAME);
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());
        maxBackoffFactor = Long.MAX_VALUE / initialDelayMillis;
    }

    public void startPost(){
        Log.i("POST_THREAD_NAME", "startPost thread: " + Thread.currentThread().getName());
//        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//            waitForIdle(0);
//        } else {
//            postWaitForIdle(0);
//        }
        postToBackgroundWithDelay(0);
    }

    private void postWaitForIdle(final int failedAttempts) {
        Log.i("POST_THREAD_NAME", "postWaitForIdle failedAttempts: " + failedAttempts +  " thread: " + Thread.currentThread().getName());
        mainHandler.post(new Runnable() {
            @Override public void run() {
                waitForIdle(failedAttempts);
            }
        });
    }
//
    private void waitForIdle(final int failedAttempts) {
        Log.i("POST_THREAD_NAME", "waitForIdle failedAttempts: " + failedAttempts + " thread: " + Thread.currentThread().getName());
        // This needs to be called from the main thread.
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override public boolean queueIdle() {
                postToBackgroundWithDelay(failedAttempts);
                return false;
            }
        });
    }

    private void postToBackgroundWithDelay(final int failedAttempts) {
        Log.i(POST_THREAD_NAME, "postToBackgroundWithDelay thread: " + Thread.currentThread().getName());
        if (failedAttempts == 10){
            return;
        }
        long exponentialBackoffFactor = (long) Math.min(Math.pow(2, failedAttempts), maxBackoffFactor);
        long delayMillis = initialDelayMillis * exponentialBackoffFactor;

        Log.i(POST_THREAD_NAME, "postToBackgroundWithDelay failedAttempts: " + failedAttempts +
                "  exponentialBackoffFactor： " + exponentialBackoffFactor + " delayMillis: " + delayMillis);
        backgroundHandler.postDelayed(new Runnable() {
            @Override public void run() {
                Log.i(POST_THREAD_NAME, "postToBackgroundWithDelay run: ");
//                postWaitForIdle( failedAttempts + 1);
                postToBackgroundWithDelay(failedAttempts + 1);

            }
        }, delayMillis);
    }
}
