package com.yxhuang.androiddailydemo.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yxhuang.androiddailydemo.MyApp

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description: 数据库
 */
@Database(entities = [ChatRoomBrief::class], version = 1)
abstract class ChatRoomBriefDatabase: RoomDatabase() {

    abstract fun chatRoomBriefDao(): ChatRoomBriefDao

    companion object{
        @Volatile
        private var INSTANCE: ChatRoomBriefDatabase?= null

        fun getDataBase():ChatRoomBriefDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        MyApp.context,
                        ChatRoomBriefDatabase::class.java,
                        "chat_room_brief_db")
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

}