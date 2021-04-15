package com.yxhuang.androiddailydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxhuang.androiddailydemo.poll.IPollMonitor;
import com.yxhuang.androiddailydemo.poll.PollMonitorManager;
import com.yxhuang.androiddailydemo.postdelay.PostDelay;

public class MainActivity extends AppCompatActivity implements IPollMonitor {

    private static final String TAG = "MainActivity_";

    private PollMonitorManager mPollMonitorManager;

    private PostDelay mPostDelay;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView();

//        mPollMonitorManager = new PollMonitorManager(this);
//        mPollMonitorManager.start();

        findViewById(R.id.tv_hello_word).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PollActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.btn_post_delay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (mPostDelay == null){
                            mPostDelay = new PostDelay(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, "PostDelay ------");
                                    mPostDelay.startPost();
                                }
                            }, 5);
                            mPostDelay.startPost();
                        }

                    }
                }).start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPollMonitorManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mPollMonitorManager.pause();
    }

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