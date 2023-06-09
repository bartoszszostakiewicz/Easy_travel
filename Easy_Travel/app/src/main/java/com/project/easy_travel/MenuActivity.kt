package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.Point
import com.project.easy_travel.ViewModel.Chat_Activity_B
import com.project.easy_travel.ViewModel.TripPointViewModel
import com.project.easy_travel.ViewModel.TripViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MenuActivity : AppCompatActivity(), OnMapReadyCallback {

    private var isMapFullScreen = false


    private lateinit var application: MainApplication

    private lateinit var tripViewModel: TripViewModel
    private lateinit var tripPointViewModel: TripPointViewModel

    private lateinit var tripID: String

    private val listPoints = mutableListOf<Point>()
    private var pointsId : List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)
        Log.d("data123","MenuActivity")

        val application = applicationContext as MainApplication



        tripViewModel = application.tripViewModel //ViewModelProvider(this).get(TripViewModel::class.java)
        tripPointViewModel = application.tripPointViewModel



        tripViewModel.data.observe(this, Observer {trip ->
           Toast.makeText(applicationContext, "Trip: ${trip.title}", Toast.LENGTH_SHORT).show()
            pointsId = trip.tripPointsID
            tripID = trip.id

            for (i in pointsId.indices) {
                tripPointViewModel.getById(pointsId[i]).observe(this, Observer { point ->
                    if (point != null) {
                        listPoints.add(point)
                        Log.d("data123","Rysowanie punktow")
                        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
                        mapFragment?.getMapAsync(this@MenuActivity)
                    }
                })

            }
        })




        val tripBtn = findViewById<Button>(R.id.trip_button)
        val helpBtn = findViewById<Button>(R.id.help)
        val chatBtn = findViewById<Button>(R.id.chat)
        val infoBtn = findViewById<Button>(R.id.information)

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

        infoBtn.setOnClickListener {
            var newActivity: Intent = Intent(applicationContext, InformationActivity::class.java)
            startActivity(newActivity)
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
