package com.yxhuang.androiddailydemo

import android.app.Application
import android.content.Context

/**
 * Created by yxhuang
 * Date: 2021/2/24
 * Description:
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
            private set
    }
}