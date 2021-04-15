package com.yxhuang.androiddailydemo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
@Entity(tableName = "chat_room_brief_table")
data class ChatRoomBrief(
        @PrimaryKey
        var channelId: Int,
        var msg:String,
        var msgTime:Long
)
