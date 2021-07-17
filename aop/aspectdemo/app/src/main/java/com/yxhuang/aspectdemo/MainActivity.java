package com.yxhuang.aspectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yxhuang.gintoinc.Annotation.DebugTrace;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMethod();
    }

    @DebugTrace
    public void testMethod(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
