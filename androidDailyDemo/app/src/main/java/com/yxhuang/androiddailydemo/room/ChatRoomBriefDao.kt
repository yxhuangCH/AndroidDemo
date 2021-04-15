package com.yxhuang.androiddailydemo.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
@Dao
interface ChatRoomBriefDao {

    /**
     * 插入数据
     * @param chatRoomBrief
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBrief(chatRoomBrief: ChatRoomBrief)

    @Delete
    fun deletedBrief(chatRoomBrief: ChatRoomBrief)

    @Query("SELECT * FROM chat_room_brief_table")
    fun getAllBrief(): Flow<List<ChatRoomBrief>>

}