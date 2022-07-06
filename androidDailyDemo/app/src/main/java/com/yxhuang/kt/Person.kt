package com.yxhuang.kt

/**
 * Created by yxhuang
 * Date: 2022/2/10
 * Description:
 */
class Person {

    @JvmField
    var name: String? = null

    companion object{
        @JvmStatic
        val age = 1
//
        @JvmStatic
        fun getData(){

        }
    }
}