package com.yxhuang.jni.binderdemo.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RemoteService extends Service {
    public RemoteService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BookStub();
    }
}
