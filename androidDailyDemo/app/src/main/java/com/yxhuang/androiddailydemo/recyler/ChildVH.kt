package com.yxhuang.androiddailydemo.recyler

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.yxhuang.androiddailydemo.recyler.Type.TYPE_CHILD

/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 */
class ChildVH(view: View) : ItemVH(view) {
    val text1: TextView = view.findViewById<TextView>(android.R.id.text1)
    val text2: TextView = view.findViewById<TextView>(android.R.id.text2)

    init {
        text1.setTextColor(Color.LTGRAY)
        text2.setTextColor(Color.GREEN)
    }


    override fun getType(): Int {
        return TYPE_CHILD
    }
}