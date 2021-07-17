package com.yxhuang.temp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yxhuang.annotationlib.BindView;
import com.yxhuang.annotationlib.MyAnnotation;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_text)
    TextView mTvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @MyAnnotation(action = 2)
    public void test() {
        SharedPreferences sharedPreferences = getSharedPreferences("", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();


    }

}
