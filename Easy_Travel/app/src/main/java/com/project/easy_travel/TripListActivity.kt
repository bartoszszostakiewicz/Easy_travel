package com.project.easy_travel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easy_travel.R
import com.google.android.material.button.MaterialButton
import com.project.easy_travel.Model.TripCell

data class TripListItem (
    val name: String
)


class TripListActivity : AppCompatActivity() {
    private lateinit var tripActive: TripActive
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_list)

        var tripList = findViewById<RecyclerView>(R.id.tripList)
        var backBtn = findViewById<Button>(R.id.logOutButton)

        val tripItems: MutableList<TripCell> = mutableListOf()
        tripItems.add(TripCell("Szczecin Trip", false, "..."))
        tripItems.add(TripCell("Zakopane Trip", false, "..."))
        tripItems.add(TripCell("Gdansk Trip", false, "..."))

        tripActive = TripActive(tripItems, this, Intent(applicationContext, MenuActivity::class.java), R.layout.trip_list_element)

        tripList.adapter = tripActive
        tripList.layoutManager = LinearLayoutManager(this)

        backBtn.setOnClickListener {
            this.finish()
        }
    }
}