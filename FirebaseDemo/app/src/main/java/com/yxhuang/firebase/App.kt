package com.yxhuang.firebase

import android.app.Application

/**
 * Created by yxhuang
 * Date: 2021/3/30
 * Description:
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        initFirebase()
    }

//    private fun initFirebase(){
//        val options = FirebaseOptions.Builder()
//            .setProjectId("chikii")
//            .setApplicationId("1:430375810196:android:a57af4482b8f563959f081")
//            .setApiKey("AIzaSyBft3VXmmg2W7TsGVFcE_-rdWK8CS4Dijg")
//            .setStorageBucket("chikii.appspot.com")
//            .build()
//
//        Firebase.initialize(baseContext, options, FirebaseActivity.FIREBASE_WEB_ENTRY)
//
//         Firebase.app(FirebaseActivity.FIREBASE_WEB_ENTRY)
//
//        val optionss = Firebase.app(FirebaseActivity.FIREBASE_WEB_ENTRY).options
//        Log.i("App_options", "optionss $optionss")
//    }
}