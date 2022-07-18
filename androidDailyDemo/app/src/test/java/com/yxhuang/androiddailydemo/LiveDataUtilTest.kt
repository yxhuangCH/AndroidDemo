/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yxhuang.androiddailydemo

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Created by yxhuang
 * Date: 2022/5/3
 * Description:
 *  Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 *  Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 *
 *  参考：
 *      - 1. https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
 *      - 2.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValueTest(
    time: Long = 2, timeUnit: TimeUnit = java.util.concurrent.TimeUnit.SECONDS, afterObserver: () -> Unit = {}
): T {
    var date: T? = null
    val latch = CountDownLatch(1)
    val observer = object :Observer<T>{
        override fun onChanged(t: T) {
            date = t
            latch.countDown()
            this@getOrAwaitValueTest.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserver.invoke()
        if (!latch.await(time,timeUnit)){
            throw java.util.concurrent.TimeoutException("LiveData value was never set.")
        }
    } finally {
        this.removeObserver(observer)
    }
    @Suppress("UNCHECKED_CAST")
    return date as T
}