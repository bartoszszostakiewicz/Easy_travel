package com.example.easy_travel

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.MenuActivity
import com.project.easy_travel.Model.TripCell
import com.project.easy_travel.TripActive

class TripListPointActivity : AppCompatActivity() {
    private lateinit var tripActive: TripActive
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_plan)

        var tripList = findViewById<RecyclerView>(R.id.tripList)
        var backBtn = findViewById<Button>(R.id.buttonBack)

        val tripItems: MutableList<TripCell> = mutableListOf()
        tripItems.add(TripCell("Jasne Błonia", true, "..."))
        tripItems.add(TripCell("Jezioro Szmaragdowe", true, "..."))
        tripItems.add(TripCell("Zamek Książąt Pomorskich", true, "..."))
        tripItems.add(TripCell("Filharmonia", true, "..."))
        tripItems.add(TripCell("Muzeum Narodowe", true, "..."))
        tripItems.add(TripCell("Centrum Dialogu Przełomy", true, "..."))
        tripItems.add(TripCell("Willa Lentza", true, "..."))
        tripItems.add(TripCell("Morskie Centrum Nauki", true, "..."))
        tripItems.add(TripCell("Wiskord", true, "..."))
        tripItems.add(TripCell("Wały Chrobrego", true, "..."))

        tripActive = TripActive(tripItems, this, Intent(applicationContext, MenuActivity::class.java), R.layout.trip_plan_element)


        tripList.adapter = tripActive
        tripList.layoutManager = LinearLayoutManager(this)

        backBtn.setOnClickListener {
            this.finish()
        }
    }
}