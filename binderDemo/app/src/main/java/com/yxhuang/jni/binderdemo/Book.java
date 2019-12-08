package com.yxhuang.jni.binderdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yxhuang
 * Date: 2019/11/22
 * Description:
 */
public class Book implements Parcelable {

    private int mBookId;
    private String mBookName;

    public Book(int bookId, String bookName) {
        mBookId = bookId;
        mBookName = bookName;
    }

    protected Book(Parcel in) {
        mBookId = in.readInt();
        mBookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getBookId() {
        return mBookId;
    }

    public String getBookName() {
        return mBookName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "mBookId=" + mBookId +
                ", mBookName='" + mBookName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mBookId);
        dest.writeString(mBookName);
    }
}
