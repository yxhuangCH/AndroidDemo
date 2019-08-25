package com.yxhuang.statedialogdemo.dialogstate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by yxhuang
 * Date: 2019/8/2
 * Description:
 */
public class DialogOneState extends BaseDialogState {

    private AlertDialog mAlertDialog;

    @Override
    public void handle() {

        showPolicyDialog();
    }

    @Override
    protected void nextDialogState() {
      Log.i(TAG, "DialogOneState nextDialogState ");
      mAlertDialog.dismiss();
      mDialogContext.nextDialogState(new DialogTwoState());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showPolicyDialog() {
        if (mAlertDialog != null){
            return;
        }
       mAlertDialog = new AlertDialog.Builder(mActivity)
                .setMessage("弹窗 one")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nextDialogState();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nextDialogState();
                    }
                })
                .show();

    }
}
