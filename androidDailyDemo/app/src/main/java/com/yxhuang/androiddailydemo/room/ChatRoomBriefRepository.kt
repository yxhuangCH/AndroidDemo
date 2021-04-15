package com.yxhuang.androiddailydemo.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
class ChatRoomBriefRepository(private val chatRoomBriefDao: ChatRoomBriefDao) {

    val allBriefs: Flow<List<ChatRoomBrief>> = chatRoomBriefDao.getAllBrief()

    @WorkerThread
    suspend fun insert(chatRoomBrief: ChatRoomBrief){
        chatRoomBriefDao.insertBrief(chatRoomBrief)
    }
}