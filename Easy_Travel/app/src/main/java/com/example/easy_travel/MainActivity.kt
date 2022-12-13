package com.example.easy_travel

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
//import kotlinx.android.synthetic.main.trip_plan.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username = findViewById<TextView>(R.id.username)
        var password = findViewById<TextView>(R.id.password)
        var btn = findViewById<MaterialButton>(R.id.login_button)

        btn.setOnClickListener {
        Log.d("TAD", "Button clicked")


            Log.d("TAD", "Connection established");
            if (username.text.toString() == "admin" && password.text.toString() == "") {
                var mes1 = Toast.makeText(applicationContext, "LOGIN SUCCESFUL", Toast.LENGTH_LONG)
                mes1.show()

                var nowaAktywnosc: Intent = Intent(applicationContext, MenuActivity::class.java)
                startActivity(nowaAktywnosc)
            }

            else {
                var mes2 = Toast.makeText(applicationContext, "LOGIN FAILED", Toast.LENGTH_LONG)
                mes2.show()
            }
        }

    }

//    //Plan wycieczki
//    private lateinit var tripActive: TripActive
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.trip_plan)
//
//        val tripItems: MutableList<TripCell> = mutableListOf()
//        tripItems.add(TripCell("Jasne Błonia", true, "..."))
//        tripItems.add(TripCell("Jezioro Szmaragdowe", true, "..."))
//        tripItems.add(TripCell("Zamek Książąt Pomorskich", true, "..."))
//        tripItems.add(TripCell("Filharmonia", true, "..."))
//        tripItems.add(TripCell("Muzeum Narodowe", true, "..."))
//        tripItems.add(TripCell("Centrum Dialogu Przełomy", true, "..."))
//        tripItems.add(TripCell("Willa Lentza", true, "..."))
//        tripItems.add(TripCell("Morskie Centrum Nauki", true, "..."))
//        tripItems.add(TripCell("Wiskord", true, "..."))
//        tripItems.add(TripCell("Wały Chrobrego", true, "..."))
//
//        tripActive = TripActive(tripItems)
//
//        tripList.adapter = tripActive
//        tripList.layoutManager = LinearLayoutManager(this)
//
//
//        buttonBack.setOnClickListener {
//            val newActive: Intent = Intent(applicationContext, ListaWycieczek::class.java)
//            startActivity(newActive)
//        }
//    }
}