// AddBookListener.aidl
package com.yxhuang.jni.binderdemo;
import com.yxhuang.jni.binderdemo.Book;

// Declare any non-default types here with import statements

interface AddBookListener {

    void addBook(in Book book);
}
