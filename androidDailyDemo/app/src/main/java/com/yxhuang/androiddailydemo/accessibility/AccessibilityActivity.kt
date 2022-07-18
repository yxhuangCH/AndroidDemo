package com.yxhuang.androiddailydemo.accessibility

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxhuang.androiddailydemo.databinding.ActivityAccessibilityBinding

class AccessibilityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccessibilityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccessibilityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager

        val dataList = createTestData()
        val adapter = AccessibilityAdapter2(this, dataList)
        adapter.setOnCheckListener(object : OnCheckListener2 {
            override fun onChanged(position: Int, isChecked: Boolean) {
                binding.recyclerView.postDelayed(
                    {
                        dataList[position].isSelect = isChecked
                        adapter.notifyItemChanged(position)
//                        adapter.notifyDataSetChanged()
                     }, 3_000)
            }
        })
        binding.recyclerView.adapter = adapter
    }

    private fun createTestData(): List<AccessibilityEntry> {
        val array = arrayListOf<AccessibilityEntry>()
        array.add(AccessibilityEntry("test -- 1", true))
        array.add(AccessibilityEntry("test -- 2", false))
        array.add(AccessibilityEntry("test -- 3", false))
        array.add(AccessibilityEntry("test -- 4", false))
        array.add(AccessibilityEntry("test -- 5", false))
        array.add(AccessibilityEntry("test -- 6", true))
        array.add(AccessibilityEntry("test -- 7", false))
        array.add(AccessibilityEntry("test -- 8", false))
        array.add(AccessibilityEntry("test -- 9", false))

        array.add(AccessibilityEntry("test -- 11", true))
        array.add(AccessibilityEntry("test -- 12", false))
        array.add(AccessibilityEntry("test -- 13", false))
        array.add(AccessibilityEntry("test -- 14", false))
        array.add(AccessibilityEntry("test -- 15", false))
        array.add(AccessibilityEntry("test -- 16", true))
        array.add(AccessibilityEntry("test -- 17", false))
        array.add(AccessibilityEntry("test -- 18", false))
        array.add(AccessibilityEntry("test -- 19", false))

        array.add(AccessibilityEntry("test -- 21", true))
        array.add(AccessibilityEntry("test -- 22", false))
        array.add(AccessibilityEntry("test -- 23", false))
        array.add(AccessibilityEntry("test -- 24", false))
        array.add(AccessibilityEntry("test -- 25", false))
        array.add(AccessibilityEntry("test -- 26", true))
        array.add(AccessibilityEntry("test -- 27", false))
        array.add(AccessibilityEntry("test -- 28", false))
        array.add(AccessibilityEntry("test -- 29", false))
        return array
    }

}