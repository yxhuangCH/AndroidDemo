package com.yxhuang.androiddailydemo.recyler

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxhuang.androiddailydemo.R
import java.util.*


/**
 * Created by yxhuang
 * Date: 2021/1/12
 * Description:
 *  RecyclerView 的使用例子
 */
open class RecyclerActivity : Activity() {

    private val groupNames = arrayOf("A", "B", "C", "D", "E", "F", "G")
    private var mItems: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_layout)


         for (i in groupNames.indices){
            val group = GroupItem()
            group.position = i
            group.title = groupNames[i]
            mItems.add(group)
            val count = (Math.random() * 10).toInt() % 4 + 1
            for (j in 0 .. count) {
                val child = ChildItem()
                child.position = j
                child.groupPosition = i
                child.groupName = group.title
                mItems.add(child)
            }
        }
        val mRecyclerView: RecyclerView = findViewById(R.id.recycler_view)

        //GridLayoutManager layoutManage = new GridLayoutManager(this, 4);
        val layoutManage = LinearLayoutManager(this)
        layoutManage.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManage
        val mAdapter = RecyclerViewAdapter(mItems)
        mRecyclerView.adapter = mAdapter
    }


}