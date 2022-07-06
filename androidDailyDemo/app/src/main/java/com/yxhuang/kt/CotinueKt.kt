package com.yxhuang.kt

import android.nfc.Tag
import android.util.Log
import kotlinx.coroutines.*

/**
 * Created by yxhuang
 * Date: 2021/12/15
 * Description:
 */

const val TAG = "CotinueKt"

fun main() {
    println("Hello word")
    runBlocking {
        println( "Hello word 1 ThreadName:" + Thread.currentThread().name)
        val userToken = getUserToken()
        println("println2 userToken: $userToken")
        val userId = getUserId(userToken)
        println("println3 userId: $userId")
    }
    println("Hello word 3")
}

private suspend fun getUserToken(): String {
    withContext(Dispatchers.IO) {
        println("getUserToken delay 1")
        delay(2000)
        println("getUserToken delay 2")
    }
    return "user"
}

private suspend fun getUserId(userToken: String): Long {
    println("getUserId delay 1")
    delay(1000)
    println("getUserId delay 2")
    return 1000L
}