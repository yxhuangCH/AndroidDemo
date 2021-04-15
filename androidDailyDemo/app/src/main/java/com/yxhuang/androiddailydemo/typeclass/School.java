package com.yxhuang.androiddailydemo.typeclass;

/**
 * Created by yxhuang
 * Date: 2021/3/13
 * Description:
 */
public class School extends Place<Teacher> {
    @Override
    void addHuman(Teacher human) {
        human.say("");
    }
}
