package com.yxhuang.jni.binderdemo.remote;

import android.os.RemoteException;
import android.util.Log;

import com.yxhuang.jni.binderdemo.IBookManagerInterface;

/**
 * Created by yxhuang
 * Date: 2019/11/22
 * Description:
 */
public class BookStub extends IBookManagerInterface.Stub implements IBookManagerInterface {

    private static final String TAG = "BookStub";

    @Override
    public void addBook() throws RemoteException {

        Log.i(TAG, "addBook");

    }
}
