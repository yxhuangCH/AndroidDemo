package com.yxhuang.androiddailydemo.recyler

import android.graphics.Color
import android.view.View
import android.widget.TextView

/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 */
class GroupVH(view: View) : ItemVH(view) {

    var text1 : TextView = view.findViewById<TextView>(android.R.id.text1)

    init {
        text1.setBackgroundColor(Color.RED)
    }

    override fun getType(): Int {
        return Type.TYPE_GROUP
    }
}