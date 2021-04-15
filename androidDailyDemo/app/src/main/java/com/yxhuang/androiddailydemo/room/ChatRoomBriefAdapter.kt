package com.yxhuang.androiddailydemo.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yxhuang.androiddailydemo.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by yxhuang
 * Date: 2021/2/25
 * Description:
 */
class ChatRoomBriefAdapter : ListAdapter<ChatRoomBrief, ChatRoomBriefAdapter.BriefViewHolder>(BRIEF_COMPARATOR) {

    companion object{
        private val BRIEF_COMPARATOR = object : DiffUtil.ItemCallback<ChatRoomBrief>(){
            override fun areItemsTheSame(oldItem: ChatRoomBrief, newItem: ChatRoomBrief): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ChatRoomBrief, newItem: ChatRoomBrief): Boolean {
                return oldItem.channelId == newItem.channelId
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BriefViewHolder {
        return BriefViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BriefViewHolder, position: Int) {
        val count = itemCount
        val current = getItem(position)
        holder.bind(current)
    }



    class BriefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val channelIdTv : TextView = itemView.findViewById(R.id.channel_id)
        private val channelMsgTv: TextView = itemView.findViewById(R.id.channel_msg)
        private val channelMsgTimeTv: TextView = itemView.findViewById(R.id.msg_time)

        fun bind(chatRoomBrief: ChatRoomBrief){
            chatRoomBrief.let {
                channelIdTv.text = "${it.channelId}"
                channelMsgTv.text = it.msg
                channelMsgTimeTv.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(it.msgTime)
            }
        }

        companion object{
            fun create(parent: ViewGroup):BriefViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.chat_room_brief_item_layout, parent, false)
                return BriefViewHolder(view)
            }
        }
    }


}