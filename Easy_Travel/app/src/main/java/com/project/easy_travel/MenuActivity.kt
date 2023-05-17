package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.easy_travel.ViewModel.Chat_Activity_B
import com.project.easy_travel.ViewModel.MainViewModel
import com.project.easy_travel.ViewModel.TripViewModel


class MenuActivity : AppCompatActivity() {
    private lateinit var application: MainApplication

    private lateinit var tripViewModel: TripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)

        val application = applicationContext as MainApplication

        tripViewModel = application.tripViewModel //ViewModelProvider(this).get(TripViewModel::class.java)


        tripViewModel.data.observe(this, Observer {trip ->
           Toast.makeText(applicationContext, "Trip: ${trip.title}", Toast.LENGTH_SHORT).show()
        })

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

            var newActivity: Intent = Intent(applicationContext, Chat_Activity_B::class.java)
            startActivity(newActivity)
        }



    }
}
