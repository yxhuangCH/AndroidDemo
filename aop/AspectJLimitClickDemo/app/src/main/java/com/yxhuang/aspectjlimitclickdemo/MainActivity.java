package com.yxhuang.aspectjlimitclickdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yxhuang.clicklimit.annotation.ClickLimit;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TextView tvSay = findViewById(R.id.tv_say);
        tvSay.setOnClickListener(new View.OnClickListener() {

            @ClickLimit(value = 1000)
            @Override
            public void onClick(View v) {
                Log.i(TAG, "-----onClick----");
                showToast();
            }
        });
    }


    private void showToast() {
        Toast.makeText(MainActivity.this, "被点击", Toast.LENGTH_SHORT).show();
    }

    @ClickLimit(value = 3000)
    @OnClick(R.id.btn_click)
    public void onViewClicked(View view) {
        Log.i(TAG, "-----method onClick  execution----");
        showToast();

//        switch (view.getId()){
//            case R.id.btn_click:
//                Log.i(TAG, "-----method onClick  btn_click");
//                showToast();
//                break;
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
