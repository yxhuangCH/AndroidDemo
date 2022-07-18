package com.yxhuang.androiddailydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yxhuang.androiddailydemo.handler.HandlerActivity;
import com.yxhuang.androiddailydemo.poll.IPollMonitor;
import com.yxhuang.androiddailydemo.poll.PollMonitorManager;
import com.yxhuang.androiddailydemo.postdelay.PostDelay;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
                Intent intent = new Intent(MainActivity.this, HandlerActivity.class);
                startActivity(intent);
//                changeNext();
            }
        });


        findViewById(R.id.btn_post_delay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (mPostDelay == null) {
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


        ObjectWrapper wrapper = ObjectWrapper.wrapper(this);
        Log.i(TAG, "ObjectWrapper " + wrapper.getT().toString());
        findViewById(R.id.btnReflect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class wrapperClazz = Class.forName("com.yxhuang.androiddailydemo.ObjectWrapper");
                    Log.i(TAG, "wrapperClazz " + wrapperClazz.getSimpleName());
                    Field field = wrapperClazz.getDeclaredField("object");
                    field.setAccessible(true);
                    Log.i(TAG, "wrapperClazz field " + field.getName());
                    field.set(wrapper, null);
                    Log.i(TAG, "ObjectWrapper 2 " + wrapper.getT());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "ObjectWrapper error " + e.getMessage());
                }


            }
        });

        findViewById(R.id.btnTestGoogleAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.parse("https://adclick.g.doubleclick.net/...");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnFinalizer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 1000; i++) {
                    View view = new ImageView(MainActivity.this);
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPollMonitorManager.resume();
//        changeByte();
        testGson();
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

    private void changeByte() {
        int tenByte = 128512;
        String tenByteHex = Integer.toHexString(tenByte);
        Log.i(TAG, "changeByte tenByte= " + tenByte + " tenByteHex=" + tenByteHex);
        Log.i(TAG, "changeByte -----");
        int tenByte2 = Integer.parseInt(tenByteHex, 16);
        Log.i(TAG, "changeByte tenByte2= " + tenByte2);
    }

    private AtomicInteger mLastIndex = new AtomicInteger(0);

    private List<String> mUrlList = new ArrayList<String>() {{
        add("String 1");
        add("String 2");
        add("String 3");
    }};

    private void changeNext() {
        int currentIndex = mLastIndex.incrementAndGet();
        Log.i(TAG, "currentIndex :" + currentIndex);
        int urlIndex = currentIndex % mUrlList.size();
        Log.i(TAG, "urlIndex :" + currentIndex);
        String url = mUrlList.get(urlIndex);
        Log.i(TAG, "url :" + url);
    }

    private void testGson(){

        String text = "{\"id\":\"12\", \"name\":\"test\", \"age\":\"10\"}";

        try {
            User user = new Gson().fromJson(text, User.class);
            Log.i(TAG, "user :" + user.toString());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

    }
}