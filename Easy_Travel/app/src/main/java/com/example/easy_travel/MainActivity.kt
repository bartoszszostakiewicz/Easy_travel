package com.example.easy_travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.Przycisk)
        button.setOnClickListener{
            var message = Toast.makeText(applicationContext,"witam",Toast.LENGTH_LONG )
            message.show()
        }
    }
}