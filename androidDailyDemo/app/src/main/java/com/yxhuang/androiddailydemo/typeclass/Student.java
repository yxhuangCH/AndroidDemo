package com.yxhuang.androiddailydemo.typeclass;

/**
 * Created by yxhuang
 * Date: 2021/3/13
 * Description:
 */
public class Student implements IHuman {
    @Override
    public void say(String word) {
        System.out.println("I am student");
    }
}
