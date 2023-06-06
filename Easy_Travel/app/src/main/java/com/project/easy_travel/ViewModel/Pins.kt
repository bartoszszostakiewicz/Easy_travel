package com.project.easy_travel.ViewModel

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.project.easy_travel.MainApplication
import com.project.easy_travel.R
import com.project.easy_travel.Model.Point
import kotlin.math.abs
import kotlin.random.Random

class Pins() : AppCompatActivity(), OnMapReadyCallback{

    private val points:MutableList<Point> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TripCheck", "Work!")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pins)

        supportActionBar?.hide()


        findViewById<MaterialButton>(R.id.mark_accept).setOnClickListener{

            this.finish()
        }



    }


    fun onPlaceAdded(point: Point) {
        // Add the new point to the list
        points.add(point)

        // Get the GoogleMap instance from the SupportMapFragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            // Draw a marker for the new point
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(point.lat, point.lng))
                    .title("Marker :)")
            )

            // Move camera to the new point with zoom level
            val zoomLevel = 10f
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(point.lat, point.lng))
                .zoom(zoomLevel)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {


        val mapView = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        points.forEach { point ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(point.lat, point.lng))
                    .title("Marker :)")
            )
        }

    }




}




