package com.yxhuang.statedialogdemo.dialogstate;

import android.app.Activity;

/**
 * Created by yxhuang
 * Date: 2019/8/2
 */
public class DialogStateManager implements IDialogStateManager{

    private DialogContext mDialogContext;

    private boolean mIsStarted; // 首次启动

    @Override
    public void init(Activity activity) {
        mDialogContext = new DialogContext(activity);
    }

    @Override
    public void start() {
        if (mDialogContext != null && !mIsStarted){
            mIsStarted = true;
            // 设置第一个 state
            mDialogContext.nextDialogState(new DialogOneState());
        }
    }

    @Override
    public void onResume() {
        mDialogContext.onResume();
    }
}
