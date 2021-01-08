package com.yxhuang.androiddailydemo.anim;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yxhuang.androiddailydemo.R;

/**
 * Created by yxhuang
 * Date: 2020/12/11
 * Description:
 */
public class AnimActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity_layout);

        initView();
    }

    private void initView(){

        int textColor = getResources().getColor(R.color.colorAccent)


        RelativeLayout contentLayout = findViewById(R.id.llAnim);

        TextView tvSpeedText = new TextView(this);
        tvSpeedText.setText("0ms");
        tvSpeedText.setTextSize(6);
        tvSpeedText.setTextColor(textColor);

        RelativeLayout.LayoutParams speedLayoutParams = new RelativeLayout.LayoutParams(60, 60);
        speedLayoutParams.addRule();

        contentLayout.addView(tvSpeedText, speedLayoutParams);





    }
}
