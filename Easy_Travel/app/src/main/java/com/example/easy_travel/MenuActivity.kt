package com.example.easy_travel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)

        val tripBtn = findViewById<Button>(R.id.trip_button)

        tripBtn.setOnClickListener {
            var newActivity: Intent = Intent(applicationContext, TripListActivity::class.java)
            startActivity(newActivity)
        }
    }
}
