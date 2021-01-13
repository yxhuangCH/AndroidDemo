package com.yxhuang.androiddailydemo.recyler

/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 */
class ChildItem : Item() {

    var groupPosition = 0
    var groupName = ""

    override fun getType(): Int {
        return Type.TYPE_CHILD
    }
}