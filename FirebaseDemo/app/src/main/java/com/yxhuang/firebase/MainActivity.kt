package com.yxhuang.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvHello = findViewById<TextView>(R.id.tv_hello)
        tvHello.setOnClickListener {
            val intent = Intent(this, FirebaseActivity::class.java)
            startActivity(intent)
        }
    }
}