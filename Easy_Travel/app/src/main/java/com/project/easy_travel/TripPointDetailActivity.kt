package com.project.easy_travel

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*

class TripPointDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_plan_detail_activity)

        val application = applicationContext as MainApplication

        val tripPointViewModel = application.tripPointViewModel

        val back_button = findViewById<Button>(R.id.back_button)

        val title_txt = findViewById<TextView>(R.id.pointTitle_txt)
        val describe_txt = findViewById<TextView>(R.id.describePoint_txt)
        val startDate_txt = findViewById<TextView>(R.id.startDate_txt)
        val finishDate_txt = findViewById<TextView>(R.id.finishDate_txt)

        tripPointViewModel.data.observe(this, Observer { point ->
            title_txt.text = point.name
            describe_txt.text = point.describe

            startDate_txt.setText(
                if (point.startDate == 0L) {
                "Nie zaznaczono daty"
                } else {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    val date = Date(point.startDate)
                    dateFormat.format(date)
                })

            finishDate_txt.setText(
                if (point.finishDate == 0L) {
                    "Nie zaznaczono daty"
                } else {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    val date = Date(point.finishDate)
                    dateFormat.format(date)
                })
        })

        back_button.setOnClickListener {
            finish()
        }
    }
}