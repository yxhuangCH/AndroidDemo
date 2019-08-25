package com.yxhuang.statedialogdemo.dialogstate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by yxhuang
 * Date: 2019/8/2
 * Description:
 */
public class DialogThirdState extends BaseDialogState {

    private AlertDialog mAlertDialog;

    private boolean mIsEnd;

    @Override
    public void handle() {

        if (mIsEnd){
            return;
        }
        showPolicyDialog();
    }

    @Override
    protected void nextDialogState() {
      Log.i(TAG, "DialogThirdState nextDialogState ");

    }

    private void showPolicyDialog() {
        if (mAlertDialog != null){
            return;
        }
        mIsEnd = true;
        mAlertDialog = new AlertDialog.Builder(mActivity)
               .setMessage("弹窗 Third")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                        nextDialogState();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                        nextDialogState();
                    }
                })
                .show();

    }
}
