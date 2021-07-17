package com.yxhuang.androiddailydemo.anim;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.yxhuang.androiddailydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxhuang
 * Date: 2020/12/11
 * Description:
 */
public class AnimActivity extends Activity {

    private List<Drawable> mDrawables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity_layout);

        initDrawable();

        initView();
    }

    private void initDrawable() {
        mDrawables.add(ContextCompat.getDrawable(this, R.drawable.game_ic_live_like));
        mDrawables.add(ContextCompat.getDrawable(this, R.drawable.game_ic_live_like_drink));
        mDrawables.add(ContextCompat.getDrawable(this, R.drawable.game_ic_live_like_ice));
        mDrawables.add(ContextCompat.getDrawable(this, R.drawable.game_ic_live_like_water));
        mDrawables.add(ContextCompat.getDrawable(this, R.drawable.game_ic_live_like_laugh));
    }


    private void initView() {

        int textColor = getResources().getColor(R.color.colorAccent);


        RelativeLayout contentLayout = findViewById(R.id.llAnim);

        TextView tvSpeedText = new TextView(this);
        tvSpeedText.setText("0ms");
        tvSpeedText.setTextSize(6);
        tvSpeedText.setTextColor(textColor);

        RelativeLayout.LayoutParams speedLayoutParams = new RelativeLayout.LayoutParams(60, 60);

        contentLayout.addView(tvSpeedText, speedLayoutParams);

        LikeAnimView likeAnimView = findViewById(R.id.like_anim_view);
        likeAnimView.addDrawables(mDrawables);

        findViewById(R.id.tv_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAnimView.addFloatView();
            }
        });


    }
}
