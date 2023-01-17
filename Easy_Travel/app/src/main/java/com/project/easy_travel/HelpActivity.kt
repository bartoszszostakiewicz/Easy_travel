package com.project.easy_travel

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        var backBtn = findViewById<Button>(R.id.back_button)

        backBtn.setOnClickListener {
            this.finish()
        }
    }
}