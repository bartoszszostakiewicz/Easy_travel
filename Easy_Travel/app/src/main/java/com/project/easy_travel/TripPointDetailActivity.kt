package com.project.easy_travel

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.project.easy_travel.Model.Point
import java.text.SimpleDateFormat
import java.util.*

class TripPointDetailActivity : AppCompatActivity() , OnMapReadyCallback {

    private val listPoints = mutableListOf<Point>()
    private var pointsId : List<String> = listOf()
    private lateinit var tripID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_plan_detail_activity)

        val application = applicationContext as MainApplication

        val tripPointViewModel = application.tripPointViewModel

        val tripViewModel = application.tripViewModel

        val back_button = findViewById<Button>(R.id.back_button)

        val title_txt = findViewById<TextView>(R.id.pointTitle_txt)
        val describe_txt = findViewById<TextView>(R.id.describePoint_txt)
        val startDate_txt = findViewById<TextView>(R.id.startDate_txt)
        val finishDate_txt = findViewById<TextView>(R.id.finishDate_txt)

        tripViewModel.data.observe(this, Observer {trip ->
            pointsId = trip.tripPointsID
            tripID = trip.id

            for (i in pointsId.indices) {
                tripPointViewModel.getById(pointsId[i]).observe(this, Observer { point ->
                    if (point != null) {
                        listPoints.add(point)
                        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
                        mapFragment?.getMapAsync(this@TripPointDetailActivity)
                    }
                })

            }
        })

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


    override fun onMapReady(googleMap: GoogleMap) {
        val mapView = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        val titleList = mutableListOf<String>()
        val descriptionList = mutableListOf<String>()
        val dateListStart = mutableListOf<Long>()
        val dateListFinish = mutableListOf<Long>()
        val dateListStartString = mutableListOf<String>()
        val dateListFinishString = mutableListOf<String>()

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        val currentDate = Date()

        listPoints.forEach { point ->

            titleList.add(point.name)
            descriptionList.add(point.describe)
            dateListStart.add(point.startDate)
            dateListFinish.add(point.finishDate)
            dateListStartString.add("Data rozpoczęcia: "+sdf.format(point.startDate).toString())
            dateListFinishString.add("Data zakonczenia: "+sdf.format(point.finishDate).toString())


            if(point.startDate <= currentDate.time && point.finishDate >= currentDate.time)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(point.toLatLng())
                        .title(point.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
            else if(point.startDate > currentDate.time)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(point.toLatLng())
                        .title(point.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                )
            else if(point.finishDate <= currentDate.time)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(point.toLatLng())
                        .title(point.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
            else if(point.startDate == currentDate.time || point.finishDate == currentDate.time
                || (point.startDate < currentDate.time && point.finishDate > currentDate.time))
                googleMap.addMarker(
                    MarkerOptions()
                        .position(point.toLatLng())
                        .title(point.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
        }

        val builder = LatLngBounds.builder()
        for (point in listPoints) {
            builder.include(point.toLatLng())
        }
        val bounds = builder.build()
        val padding = 100
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.moveCamera(cameraUpdate)

        googleMap.setOnMarkerClickListener { marker ->
            var i = titleList.indexOf(marker.title)

            val title = marker.title
            val description = descriptionList[i] + "\n" + dateListStartString[i] + "\n" + dateListFinishString[i]
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton("Wróć", null)
                .show()

            true
        }

    }


}