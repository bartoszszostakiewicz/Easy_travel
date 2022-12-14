package com.project.easy_travel

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TripListActivity : AppCompatActivity() {
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

        tripActive = TripActive(tripItems)


        tripList.adapter = tripActive
        tripList.layoutManager = LinearLayoutManager(this)

        backBtn.setOnClickListener {
            this.finish()
        }
    }
}