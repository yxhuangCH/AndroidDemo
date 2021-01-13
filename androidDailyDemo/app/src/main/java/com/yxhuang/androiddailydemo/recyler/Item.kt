package com.yxhuang.androiddailydemo.recyler

/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 */
abstract class Item {

    var position: Int = 0

    abstract fun getType():Int

}