package com.yxhuang.androiddailydemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.yxhuang.androiddailydemo.utils.DensityUtil;

/**
 * Created by yxhuang
 * Date: 2021/8/28
 * Description:
 */
public class FrameLayoutAnimActivity extends Activity {

    private static final String TAG = "AnimActivity_";
    private static final long ANIM_DURATION = 2000L; //动画进出时长

    private FrameLayout mFrameLayout;
    private Button mBtnAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelayout_anim_activity_layout);

        mFrameLayout = findViewById(R.id.framelayout);
        mBtnAnim = findViewById(R.id.btnAnim);
        mBtnAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnim();
            }
        });
    }

    private void addAnim() {
        TextView textView = new TextView(this);
        textView.setText("我是一个测试动画，从右到左");
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.anim_item_bg));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                600,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = DensityUtil.getDisplayWidth(FrameLayoutAnimActivity.this);
        // 添加到屏幕外，但是需要子 View 固定宽度
        mFrameLayout.setClipChildren(false);

        mFrameLayout.addView(textView, layoutParams);
        startAnim(textView);

    }

    private void startAnim(TextView textView) {
        textView.post(new Runnable() {
            @Override
            public void run() {
                int screenWith = DensityUtil.getDisplayWidth(FrameLayoutAnimActivity.this);
                int viewWith = textView.getMeasuredWidth();
                Log.i(TAG, "startEnterAnim screenWith: " + screenWith + " viewWith: " + viewWith);
                if (textView != null) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView,
                            "translationX",
                            0,
                            -viewWith)
                            .setDuration(ANIM_DURATION);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            textView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mFrameLayout.removeView(textView);
                                }
                            }, 2000);
                        }
                    });
                    objectAnimator.start();
                }
            }
        });
    }


}
