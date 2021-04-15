package com.yxhuang.androiddailydemo.typeclass;

/**
 * Created by yxhuang
 * Date: 2021/3/13
 * Description:
 */
public abstract class Place<T extends IHuman> {

    abstract void addHuman(T  human);
}
