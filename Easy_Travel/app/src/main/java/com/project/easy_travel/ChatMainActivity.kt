package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ChatMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_main)
        Log.e("MyTag", "Activity chat")

        var btnInfoChat = findViewById<Button>(R.id.btnInfoChat)
        var btnPrivateChat = findViewById<Button>(R.id.btnPrivateChat)
        var btnBack = findViewById<Button>(R.id.btnBack)
        Log.e("MyTag", "Button initialize")

        btnInfoChat.setOnClickListener {
            var newActivity: Intent = Intent(this, ChatActivity::class.java)
            startActivity(newActivity)
        }

        btnPrivateChat.setOnClickListener {
            var newActivity: Intent = Intent(this, ChatActivity::class.java)
            startActivity(newActivity)
        }

        btnBack.setOnClickListener {
            this.finish()
        }
    }


}