package com.yxhuang.androiddailydemo.recyler

/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 */
class GroupItem : Item() {

    var title: String = ""

    override fun getType(): Int {
        return Type.TYPE_GROUP
    }
}