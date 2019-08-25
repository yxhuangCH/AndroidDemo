package com.yxhuang.statedialogdemo.dialogstate;

import android.app.Activity;

/**
 * Created by yxhuang
 * Date: 2019/8/2
 * Description:
 */
public class DialogContext {

    private BaseDialogState mCurrentDialogState;

    private Activity mActivity;

    public DialogContext(Activity activity) {
        mActivity = activity;
    }

    public void nextDialogState(BaseDialogState baseDialogState) {
        mCurrentDialogState = baseDialogState;
        mCurrentDialogState.setDialogContext(this); //　将自身作为参数传递给　DialogState
        mCurrentDialogState.handle(); // 同时调用 DialogState#handle 方法
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void onResume() {
        if (mCurrentDialogState != null){
            mCurrentDialogState.onResume();
        }
    }
}
