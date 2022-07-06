package com.yxhuang.androiddailydemo

import android.app.Application
import android.content.Context
import android.util.Log

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this

        val minSize = 0
        for (i in 0 until minSize) {
            Log.i("MyApp", "i $i")
        }

        val min2Size = 2
        for (i in 0 until min2Size) {
            Log.i("MyApp", "min2Size i $i")
        }
    }

    companion object {
        lateinit var context: Context
            private set
    }
}