package com.yxhuang.kt

/**
 * Created by yxhuang
 * Date: 2022/2/10
 * Description:
 *  单例模式
 */

// 双重锁
class StaticUtils private constructor() {

    companion object {

        val instance: StaticUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            StaticUtils()
        }

//        private lateinit var sInstance: StaticUtils
//
//        fun getNew(): StaticUtils {
//            sInstance = if (::sInstance.isInitialized) sInstance else StaticUtils()
//            return sInstance
//        }
    }

    fun test(){

    }

}