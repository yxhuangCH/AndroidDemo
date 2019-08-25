package com.yxhuang.statedialogdemo.dialogstate;

import android.app.Activity;

/**
 * Created by yxhuang
 * Date: 2019/8/2
 * Description:
 */
public interface IDialogStateManager {

    void init(Activity activity);

    void start();

    void onResume();

}
