package com.yxhuang.androiddailydemo.recyler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 */
abstract class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun getType():Int

}