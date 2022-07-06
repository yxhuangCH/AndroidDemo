package com.yxhuang.kt

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yxhuang.androiddailydemo.R
import kotlinx.coroutines.*

class ContineActivity : AppCompatActivity() {

    companion object{
        const val TAG = "ContineActivity_"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contine)
        GlobalScope.launch {
            Log.i(TAG,"Hello word ThreadName:" + Thread.currentThread().name)
            testLaunch()
            Log.i(TAG, "Hello word 3")
        }
        Log.i(TAG, "onCreate")

        StaticUtils.instance.test()

        val person = Person()
        person.name = ""
    }

    private suspend fun testLaunch(){
        Log.i(TAG,  "Hello word 1 ThreadName:" + Thread.currentThread().name)
        val userToken = getUserToken()
        Log.i(TAG, "println2 userToken: $userToken")
        val userId = getUserId(userToken)
        Log.i(TAG, "println3 userId: $userId")
    }

    private suspend fun getUserToken(): String {
        withContext(Dispatchers.IO) {
            Log.i(TAG, "getUserToken delay 1")
            delay(2000)
            Log.i(TAG, "getUserToken delay 2")
        }
        return "user"
    }

    private suspend fun getUserId(userToken: String): Long {
        Log.i(TAG, "getUserId delay 1")
        delay(1000)
        Log.i(TAG, "getUserId delay 2")
        return 1000L
    }
}