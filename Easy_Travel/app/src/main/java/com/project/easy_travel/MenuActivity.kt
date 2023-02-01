package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity



class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)


        var btn = findViewById<Button>(R.id.button5)

        btn.setOnClickListener{
            var database_test: Intent = Intent(applicationContext,Database_test::class.java)
            startActivity(database_test)

        }


        val tripBtn = findViewById<Button>(R.id.trip_button)
        val helpBtn = findViewById<Button>(R.id.help)
        val chatBtn = findViewById<Button>(R.id.chat)

        tripBtn.setOnClickListener {
            var newActivity: Intent = Intent(applicationContext, TripListPointActivity::class.java)
            startActivity(newActivity)
        }
        helpBtn.setOnClickListener {
            var newActivity: Intent = Intent(applicationContext, HelpActivity::class.java)
            startActivity(newActivity)
        }
        chatBtn.setOnClickListener {
            var newActivity: Intent = Intent(applicationContext, ChatMainActivity::class.java)
            startActivity(newActivity)
        }



    }
}
