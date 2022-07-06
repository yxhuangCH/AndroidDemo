package com.yxhuang.kt;

/**
 * Created by yxhuang
 * Date: 2022/2/10
 * Description:
 */
public class Client {

    public static void main(String[] args) {
        Person person = new Person();
        // 加 jvmField
        String name = person.name;

        int age = Person.Companion.getAge();

        // @JvmStatic
        Person.getAge();
        Person.getData();
    }
}
