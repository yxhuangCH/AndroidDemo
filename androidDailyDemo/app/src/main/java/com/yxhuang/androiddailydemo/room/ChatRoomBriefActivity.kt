package com.yxhuang.androiddailydemo.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxhuang.androiddailydemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import androidx.lifecycle.observe

/**
 * Created by yxhuang
 * Date: 2021/2/25
 * Description:
 */
class ChatRoomBriefActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ChatRoomBriefActivity_"
    }

    private val newWordActivityRequestCode = 1

    private val dataBase by lazy {
        ChatRoomBriefDatabase.getDataBase()
    }

    private val repository by lazy {
        ChatRoomBriefRepository(dataBase.chatRoomBriefDao())
    }

    private val viewModel: ChatRoomBriefViewModel by viewModels {
        ChatRoomBriefViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room_brief)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ChatRoomBriefAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allBrief.observe(owner = this) { chatRoomBriefs ->
            chatRoomBriefs.forEach {
                Log.i(TAG, "observe  it $it")
            }
            chatRoomBriefs.let {
                adapter.submitList(it)
            }
        }

        findViewById<Button>(R.id.btn_new).setOnClickListener {
            val intent = Intent(this, ChatRoomAddBriefActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val channelId = data?.getStringExtra(ChatRoomAddBriefActivity.EXTRA_REPLY_ID)
            val channelMsg = data?.getStringExtra(ChatRoomAddBriefActivity.EXTRA_REPLY_MSG)
            val channelMsgTime = data?.getLongExtra(ChatRoomAddBriefActivity.EXTRA_REPLY_TIME, 0L)

            Log.i(TAG, "channelId $channelId channelMsg $channelMsg channelMsgTime $channelMsgTime")
            if (channelId != null && channelMsg != null && channelMsgTime != null) {
                val chatRoomBrief = ChatRoomBrief(channelId = channelId.toInt(), msg = channelMsg, msgTime = channelMsgTime)
                viewModel.insert(chatRoomBrief)
            }
        }
    }

    private fun getTestData():List<ChatRoomBrief>{
        val list = ArrayList<ChatRoomBrief>()
        list.add(ChatRoomBrief(11, "dddd", 1888080))
        list.add(ChatRoomBrief(12, "eee", 890000))
        list.add(ChatRoomBrief(13, "fffff", 18880810))

        return list
    }


}