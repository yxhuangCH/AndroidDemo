package com.yxhuang.androiddailydemo.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yxhuang.androiddailydemo.R

/**
 * Created by yxhuang
 * Date: 2021/2/25
 * Description:
 */
class ChatRoomAddBriefActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "ChatRoomAddBrief_"
        const val EXTRA_REPLY_ID = "extra_reply_id"
        const val EXTRA_REPLY_MSG = "extra_reply_msg"
        const val EXTRA_REPLY_TIME = "extra_reply_time";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room_add_brief)

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val channelId = findViewById<EditText>(R.id.edt_id).text.toString()
            val msg = findViewById<TextView>(R.id.edt_msg).text.toString()
            val msgTime = System.currentTimeMillis()

            try {
                val replyIntent = Intent()
                replyIntent.putExtra(EXTRA_REPLY_ID, channelId)
                replyIntent.putExtra(EXTRA_REPLY_MSG, msg)
                replyIntent.putExtra(EXTRA_REPLY_TIME, msgTime)
                setResult(Activity.RESULT_OK, replyIntent)
            } catch (e: Exception) {
                Log.e(TAG, "Exception $e")
            }

            finish()
        }
    }
}