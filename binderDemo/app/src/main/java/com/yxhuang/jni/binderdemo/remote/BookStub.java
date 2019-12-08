package com.yxhuang.jni.binderdemo.remote;

import android.os.RemoteException;
import android.util.Log;

import com.yxhuang.jni.binderdemo.AddBookListener;
import com.yxhuang.jni.binderdemo.Book;
import com.yxhuang.jni.binderdemo.IBookManagerInterface;

/**
 * Created by yxhuang
 * Date: 2019/11/22
 * Description:
 */
public class BookStub extends IBookManagerInterface.Stub implements IBookManagerInterface {

    private static final String TAG = "BookStub";

    private Book mBook;
    private AddBookListener mAddBookListener;

    @Override
    public void addBook(Book book) throws RemoteException {
        Log.i(TAG, "addBook "  + book.toString() + "\nThread =" + Thread.currentThread().getName() +
                " thread id=" + Thread.currentThread().getId());
        mBook = new Book(book.getBookId(), book.getBookName());
        if (mAddBookListener != null){
            mAddBookListener.addBook(mBook);
        }
    }

    @Override
    public Book getBook(int id) throws RemoteException {
        Log.i(TAG, "getBook  id = "  + id + "\nThread =" + Thread.currentThread().getName() +
                " thread id=" + Thread.currentThread().getId());
        // 子进程的 Binder　线程池
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mBook;
    }

    @Override
    public void addBookListener(AddBookListener listener) throws RemoteException {
        Log.i(TAG, "AddBookListener " + "\nThread =" + Thread.currentThread().getName() +
                " thread id=" + Thread.currentThread().getId());
        mAddBookListener = listener;

    }
}
