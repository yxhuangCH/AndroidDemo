package com.yxhuang.androiddailydemo.postdelay;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yxhuang
 * Date: 2020/11/12
 * Description:
 *  一个规律指数延迟的类, 1, 2, 4, 8, 16, 32 ...
 */
public class PostDelay {

    private static final String TAG = "PostDelay";

    static final String POST_THREAD_NAME = "post_thread";

    private Handler backgroundHandler;
    private Handler mainHandler;
    private final long initialDelayMillis = 1000L;
    private final long maxBackoffFactor;

    private Runnable mTask;
    private int mMaxAttempt = 10;
    private AtomicBoolean mQuited =  new AtomicBoolean(false);
    private AtomicInteger mFailedAttemptCount  = new AtomicInteger(1);

    public PostDelay(@NonNull Runnable task, int maxAttempt) {
        mainHandler = new Handler(Looper.getMainLooper());
        HandlerThread handlerThread = new HandlerThread(POST_THREAD_NAME);
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());
        maxBackoffFactor = Long.MAX_VALUE / initialDelayMillis;

        mTask = task;
        mMaxAttempt = maxAttempt;
    }

    public void startPost() {
        Log.i("POST_THREAD_NAME", "startPost thread: " + Thread.currentThread().getName());
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            waitForIdle(mFailedAttemptCount.get());
        } else {
            postWaitForIdle(mFailedAttemptCount.get());
        }
    }

    public void quitPost(){
        Log.i("POST_THREAD_NAME","quitPost");
        mQuited.set(true);
    }

    private void postWaitForIdle(final int failedAttempts) {
        Log.i("POST_THREAD_NAME", "postWaitForIdle failedAttempts: " + failedAttempts + " thread: " + Thread.currentThread().getName());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                waitForIdle(failedAttempts);
            }
        });
    }


    private void waitForIdle(final int failedAttempts) {
        Log.i("POST_THREAD_NAME", "waitForIdle failedAttempts: " + failedAttempts + " thread: " + Thread.currentThread().getName());
        // This needs to be called from the main thread.
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                postToBackgroundWithDelay(failedAttempts);
                return false;
            }
        });
    }

    private void postToBackgroundWithDelay(final int failedAttempts) {
        Log.i(POST_THREAD_NAME, "postToBackgroundWithDelay thread: " + Thread.currentThread().getName());
        if (failedAttempts == mMaxAttempt) {
            backgroundHandler.getLooper().quitSafely();
            return;
        }
        long exponentialBackoffFactor = (long) Math.min(Math.pow(2, failedAttempts), maxBackoffFactor);
        long delayMillis = initialDelayMillis * exponentialBackoffFactor;

        Log.i(POST_THREAD_NAME, "postToBackgroundWithDelay failedAttempts: " + failedAttempts +
                "  exponentialBackoffFactor： " + exponentialBackoffFactor + " delayMillis: " + delayMillis);
        backgroundHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mQuited.get()){
                    backgroundHandler.getLooper().quitSafely();
                    return;
                }
                Log.i(POST_THREAD_NAME, "postToBackgroundWithDelay run: ");
                mTask.run();
                mFailedAttemptCount.incrementAndGet();
//                postWaitForIdle(failedAttempts + 1);
            }
        }, delayMillis);
    }
}
