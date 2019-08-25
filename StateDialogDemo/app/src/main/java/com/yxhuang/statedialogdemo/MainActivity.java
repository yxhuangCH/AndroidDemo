package com.yxhuang.statedialogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yxhuang.statedialogdemo.dialogstate.DialogStateManager;
import com.yxhuang.statedialogdemo.dialogstate.IDialogStateManager;

public class MainActivity extends AppCompatActivity {

    private TextView mTvStartDialog;

    private IDialogStateManager mDialogStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvStartDialog = (TextView) findViewById(R.id.tv_start);

        // 创建 DialogStateManager 并进行初始化
        mDialogStateManager = new DialogStateManager();
        mDialogStateManager.init(this);

        mTvStartDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogStateManager.start(); // 启动状态
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialogStateManager.onResume();
    }

}
