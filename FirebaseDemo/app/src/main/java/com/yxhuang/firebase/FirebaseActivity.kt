package com.dianyun.chikii

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.crashlytics.CrashlyticsRegistrar
import com.google.firebase.crashlytics.internal.analytics.CrashlyticsOriginAnalyticsEventLogger
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport

class FirebaseActivity : AppCompatActivity() {
    companion object{
        const val FIREBASE_WEB_ENTRY = "firebase_web_entry"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)


        findViewById<TextView>(R.id.tv_test_crash).setOnClickListener {
            throw RuntimeException("test crash ")
        }
    }



}