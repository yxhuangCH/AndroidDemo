package com.yxhuang.androiddailydemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by yxhuang
 * Date: 2020/12/8
 * Description:
 */
public class FloatingActionButtonActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton mFabAdd;
    private boolean isAdd = false;
    private RelativeLayout mRlAddBill;
    private int[] llId = new int[]{R.id.ll01, R.id.ll02, R.id.ll03, R.id.ll04};
    private LinearLayout[] mLlArray = new LinearLayout[llId.length];
    private int[] mFabId = new int[]{R.id.miniFab01, R.id.miniFab02, R.id.miniFab03, R.id.miniFab04};
    private FloatingActionButton[] mFabs = new FloatingActionButton[mFabId.length];
    private AnimatorSet mAddBillTranslate1;
    private AnimatorSet mAddBillTranslate2;
    private AnimatorSet mAddBillTranslate3;
    private AnimatorSet mAddBillTranslate4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floatting_action_btn_activity_layout);

        initView();
    }

    private void initView() {
        mFabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        mRlAddBill = (RelativeLayout) findViewById(R.id.rlAddBill);
        for (int i = 0; i < llId.length; i++) {
            mLlArray[i] = (LinearLayout) findViewById(llId[i]);
        }
        for (int i = 0; i < mFabId.length; i++) {
            mFabs[i] = (FloatingActionButton) findViewById(mFabId[i]);
        }

        mAddBillTranslate1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_anim);
        mAddBillTranslate2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_anim);
        mAddBillTranslate3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_anim);
        mAddBillTranslate4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_anim);

        mFabAdd.setOnClickListener(this);
        for (int i = 0; i < mFabId.length; i++) {
            mFabs[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                mFabAdd.setImageResource(isAdd ? R.drawable.common_icon_add : R.drawable.common_icon_delete);
                isAdd = !isAdd;
                mRlAddBill.setVisibility(isAdd ? View.VISIBLE : View.GONE);
                if (isAdd) {
                    mAddBillTranslate1.setTarget(mLlArray[0]);
                    mAddBillTranslate1.start();
                    mAddBillTranslate2.setTarget(mLlArray[1]);
                    mAddBillTranslate2.setStartDelay(150);
                    mAddBillTranslate2.start();
                    mAddBillTranslate3.setTarget(mLlArray[2]);
                    mAddBillTranslate3.setStartDelay(200);
                    mAddBillTranslate3.start();
                    mAddBillTranslate4.setTarget(mLlArray[3]);
                    mAddBillTranslate4.setStartDelay(250);
                    mAddBillTranslate4.start();
                }
                break;
            case R.id.miniFab01:
            case R.id.miniFab02:
            case R.id.miniFab03:
            case R.id.miniFab04:
                hideFABMenu();
                break;
            default:
                break;
        }
    }

    private void hideFABMenu() {
        mRlAddBill.setVisibility(View.GONE);
        mFabAdd.setImageResource(R.drawable.common_icon_add);
        isAdd = false;
    }
}