package com.yxhuang.androiddailydemo.handler

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.yxhuang.androiddailydemo.databinding.ActivityHandlerBinding

class HandlerActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "HandlerActivity_"
        const val MSG_CODE = 100000
    }

    private lateinit var binding: ActivityHandlerBinding

    private var mBusinessThread:Thread ?= null
    private var mBusinessThreadStarted = false
    private var mBusinessThreadHandler:BusinessThreadHandler ?= null

    private lateinit var mViewModel: HandlerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startBusinessThread()

        binding.btnStart.setOnClickListener {
//            for (i in 1..1000){
//                mBusinessThreadHandler?.sendMessage(MSG_CODE, i, 0)
//            }
            val intent = Intent(this, HandlerActivity::class.java)
            startActivity(intent)
        }


        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "onProgressChanged progress:$progress")
                mBusinessThreadHandler?.sendMessage(MSG_CODE, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.d(TAG, "onStartTrackingTouchs")

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d(TAG, "onStopTrackingTouch")
            }
        })

        val viewModel: HandlerViewModel = ViewModelProvider(this).get(HandlerViewModel::class.java)

        viewModel.isTest.observe(this, {
          Log.i(TAG, "isTest $it ")
        })
    }

    private fun startBusinessThread(){
        if (mBusinessThreadStarted){
            return
        }
        mBusinessThreadStarted = true
        mBusinessThread = Thread {
            Looper.prepare()
            mBusinessThreadHandler = BusinessThreadHandler()
            Looper.loop()
        }
        mBusinessThread!!.start()
    }


    class BusinessThreadHandler : Handler() {

        fun sendMessage(what:Int, arg1:Int, arg2:Int):Boolean{
            removeMessages(what) // 清理消息队列中未处理的请求
            val msg = obtainMessage(what, arg1, arg2)
            return super.sendMessage(msg)
        }

        override fun handleMessage(msg: Message) {
           when(msg.what){
               MSG_CODE ->{
                   // 这里进行耗时操作
                   Log.d(TAG, "handleMessage MSG_CODE msg:${msg.arg1}")

               }
               else ->{
                   Log.d(TAG, "handleMessage msg: ${msg.data}")
               }
           }
        }
    }

}