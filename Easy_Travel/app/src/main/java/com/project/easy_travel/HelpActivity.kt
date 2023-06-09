package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.ViewModel.Chat_Activity_B

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        var backBtn = findViewById<Button>(R.id.back_button)

        backBtn.setOnClickListener {
            this.finish()
        }

        findViewById<Button>(R.id.organizer_chat_button).setOnClickListener {
            val newActivity: Intent = Intent(applicationContext, Chat_Activity_B::class.java)
            newActivity.putExtra("OnlyOrganizer", true)
            startActivity(newActivity)
        }

    }
}