package com.yxhuang.androiddailydemo.accessibility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import com.yxhuang.androiddailydemo.R

/**
 * Created by yxhuang
 * Date: 2022/7/13
 * Description:
 */
class AccessibilityAdapter(
        private val context: Context, private val list: List<AccessibilityEntry>
) : RecyclerView.Adapter<AccessibilityAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item_View = view.findViewById<LinearLayout>(R.id.item_view)
        val text = view.findViewById<TextView>(R.id.tv_item)
        val checkBox: CheckBox = view.findViewById<CheckBox>(R.id.checkbox)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_circular)
    }

    private var mOnCheckListener: OnCheckListener? = null

    fun setOnCheckListener(onCheckListener: OnCheckListener) {
        mOnCheckListener = onCheckListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_accessibility_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = list[position]
        holder.text.text = entry.content
        holder.checkBox.isChecked = entry.isSelect


        progressBar(false, holder.progressBar)
        setCheckBox(holder.checkBox)
        setItemView(holder.item_View, position, entry, holder.checkBox)

        holder.checkBox.setOnClickListener {
            progressBar(true, holder.progressBar)
            mOnCheckListener?.onChanged(position, holder.checkBox.isChecked)
        }
        holder.item_View.setOnClickListener {
            progressBar(true, holder.progressBar)
            mOnCheckListener?.onChanged(position, !holder.checkBox.isChecked)
        }
    }

    private fun progressBar(isShow:Boolean, progressBar: ProgressBar){
        progressBar.visibility = if (isShow){
            View.VISIBLE
        } else {
            progressBar.isClickable = false
            View.GONE
        }
        if (isShow){
            progressBar.contentDescription = "loading test"
            progressBar.postDelayed(
                {
                    ViewCompat.setAccessibilityDelegate(progressBar, object : AccessibilityDelegateCompat(){
                        override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat) {
                            super.onInitializeAccessibilityNodeInfo(host, info)
                            val nodeInfoCompat =  AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                                AccessibilityNodeInfoCompat.ACTION_CLICK, "loading test")
                            info.addAction(nodeInfoCompat)
                            info.className = null
                        }
                    })
                    progressBar.performAccessibilityAction(AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS, null)
                }, 300)
        }
    }

    private fun setItemView(itemView: View, position: Int, entry: AccessibilityEntry, checkBox: CheckBox){
        itemView.contentDescription = "Movie $position ${entry.content},stars"
        itemView.postDelayed(
            {
                itemView.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
                itemView.requestFocus()
                itemView.performAccessibilityAction(AccessibilityNodeInfo.ACTION_FOCUS, null)
           }, 100)
    }

    private fun setCheckBox(checkBox: CheckBox){
        checkBox.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

interface OnCheckListener {
    fun onChanged(position: Int, isChecked: Boolean)
}


