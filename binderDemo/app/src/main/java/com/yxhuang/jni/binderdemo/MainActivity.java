package com.yxhuang.jni.binderdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yxhuang.jni.binderdemo.remote.RemoteService;

public class MainActivity extends Activity {

    private static final String TAG = "BinderMainActivity";


    private IBookManagerInterface mIBookManager;


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected name " + name.toString());
            mIBookManager = IBookManagerInterface.Stub.asInterface(service);

            if (mIBookManager != null){
                try {
                    mIBookManager.addBookListener(new AddBookListener.Stub() {
                        @Override
                        public void addBook(Book book) throws RemoteException {
                            // 主进程、主线程
                            Log.i(TAG, "addBook callback "  + book.toString() + "\nThread =" +
                                    Thread.currentThread().getName() + " thread id=" +
                                    Thread.currentThread().getId());

                        }
                    });
                } catch (RemoteException e) {
                    Log.e(TAG, "addBook error ");
                }
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected name " + name.toString());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddBook = findViewById(R.id.btn_add_book);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Book book = new Book(1234, "Android 源码分析");
                    mIBookManager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnGetBook = findViewById(R.id.btn_get_book);
        btnGetBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long startTime = System.currentTimeMillis();
                    Log.i(TAG, "get book  start time " + startTime);
                    // 调用远端方法会将调用方的线程挂起，等待结果的返回。所以不能调耗时的操作
                    // 如果想要调，需要在 worker 线程中调
                    Book book = mIBookManager.getBook(1234);
                    long endTime = System.currentTimeMillis();
                    Log.i(TAG, "get book  end time " + endTime);
                    Log.i(TAG, "get book  time " + (endTime - startTime));

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, RemoteService.class);
        this.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        if (mServiceConnection != null){
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }
}
