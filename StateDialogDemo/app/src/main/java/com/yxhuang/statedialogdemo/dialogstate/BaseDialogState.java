package com.yxhuang.statedialogdemo.dialogstate;

import android.app.Activity;

/**
 * Created by yxhuang
 * Date: 2019/8/2
 * Description:
 */
public abstract class BaseDialogState {

    protected static final String TAG = "DialogState";

    protected DialogContext mDialogContext;
    protected Activity mActivity;

    public void setDialogContext(DialogContext dialogContext) {
        mDialogContext = dialogContext;
        mActivity = dialogContext.getActivity();
    }

    // 进行自身逻辑处理
    public abstract void handle();

    /**
     * 设置下一个 state
     */
    protected abstract void nextDialogState();


    public void onResume(){

    }
}
