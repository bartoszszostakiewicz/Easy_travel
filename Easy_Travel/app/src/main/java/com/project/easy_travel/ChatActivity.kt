package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.Model.Chat
import com.project.easy_travel.Model.ChatModel
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var btnSent = findViewById<Button>(R.id.sendBtn)
        var btnBack = findViewById<Button>(R.id.btnBack)

        var edtText = findViewById<EditText>(R.id.draftEdit)

        var chatRecycleView = findViewById<RecyclerView>(R.id.chatContext)

        var chatTexts = listOf<ChatModel>(
            ChatModel(true, "Hi"),
            ChatModel(false, "Good morning")
        )

        var chatContext = ChatAdapter(chatTexts)
        chatRecycleView.adapter = chatContext
        chatRecycleView.layoutManager = LinearLayoutManager(this)

        btnSent.setOnClickListener {
            if (edtText.text.toString() != "") {
                chatTexts += ChatModel(true, edtText.text.toString())
                edtText.text.clear()

                var chatContext = ChatAdapter(chatTexts)
                chatRecycleView.adapter = chatContext
                chatRecycleView.layoutManager = LinearLayoutManager(this)
            }
        }

        btnBack.setOnClickListener {
            this.finish()
        }
    }

}