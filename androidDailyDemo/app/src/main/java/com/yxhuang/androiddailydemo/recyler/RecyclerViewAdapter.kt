package com.yxhuang.androiddailydemo.recyler

import android.R
import android.provider.ContactsContract.CommonDataKinds.Relation.TYPE_CHILD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxhuang.androiddailydemo.recyler.Type.TYPE_GROUP
import java.util.*


/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 */
class RecyclerViewAdapter(private val items:ArrayList<Item>) : RecyclerView.Adapter<ItemVH?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val view: View
        var itemVH: ItemVH? = null
        when (viewType) {
            TYPE_GROUP -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_1, parent, false)
                itemVH = GroupVH(view)
            }
            TYPE_CHILD -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_2, parent, false)
                itemVH = ChildVH(view)
            }

            else ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_2, parent, false)
                itemVH = ChildVH(view)
            }

        }
        return itemVH
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item: Item = items[position]
        when (getItemViewType(position)) {
            TYPE_GROUP -> {
                val g: GroupItem = item as GroupItem
                val groupVH = holder as GroupVH
                groupVH.text1.text = g.title
            }
            TYPE_CHILD -> {
                val c: ChildItem = item as ChildItem
                val childVH = holder as ChildVH
                childVH.text1.text = c.groupName
                childVH.text2.text = c.position.toString() + ""
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType()
    }
}