package com.yxhuang.androiddailydemo.room

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */

class ChatRoomBriefViewModel(private val repository: ChatRoomBriefRepository): ViewModel(){

    val allBrief: LiveData<List<ChatRoomBrief>> = repository.allBriefs.asLiveData()

    fun insert(chatRoomBrief: ChatRoomBrief) = viewModelScope.launch(Dispatchers.IO) {
        Log.i("ChatRoomBriefViewModel", "Thread " + Thread.currentThread().id)
        repository.insert(chatRoomBrief)
    }
}

class ChatRoomBriefViewModelFactory(private val repository: ChatRoomBriefRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatRoomBriefViewModel::class.java)){
            return ChatRoomBriefViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}