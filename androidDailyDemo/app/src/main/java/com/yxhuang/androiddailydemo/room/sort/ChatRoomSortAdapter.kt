package com.yxhuang.androiddailydemo.room.sort

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yxhuang.androiddailydemo.R
import com.yxhuang.androiddailydemo.room.ChatRoomBrief
import java.text.SimpleDateFormat

/**
 * Created by yxhuang
 * Date: 2021/2/25
 * Description:
 */
class ChatRoomSortAdapter : RecyclerView.Adapter<ChatRoomSortAdapter.BriefViewHolder>() {

    override fun getItemCount(): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomSortAdapter.BriefViewHolder {
        return ChatRoomSortAdapter.BriefViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChatRoomSortAdapter.BriefViewHolder, position: Int) {
        val count = itemCount
//        val current = getItem(position)
//        holder.bind(current)
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