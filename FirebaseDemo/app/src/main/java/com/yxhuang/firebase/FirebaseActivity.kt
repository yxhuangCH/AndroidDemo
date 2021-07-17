package com.yxhuang.firebase

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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