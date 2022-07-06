package com.yxhuang.androiddailydemo;

/**
 * Created by yxhuang
 * Date: 2021/10/22
 * Description:
 */
public class ObjectWrapper<T> {

    private final T object;

    private ObjectWrapper(T object) {
        this.object = object;
    }

    public static <T> ObjectWrapper wrapper(T object) {
        return new ObjectWrapper(object);
    }

    public Object getT(){
        return object;
    }
}
