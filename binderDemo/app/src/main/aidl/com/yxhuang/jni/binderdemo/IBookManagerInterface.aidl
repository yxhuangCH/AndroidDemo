// IBookManagerInterface.aidl
package com.yxhuang.jni.binderdemo;

import com.yxhuang.jni.binderdemo.Book;
import com.yxhuang.jni.binderdemo.AddBookListener;

// Declare any non-default types here with import statements

interface IBookManagerInterface {

    void addBook(in Book book);

    Book getBook(int id);

    void addBookListener(AddBookListener listener);

}
