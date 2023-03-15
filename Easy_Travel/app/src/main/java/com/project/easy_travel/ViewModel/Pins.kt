package com.project.easy_travel.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.project.easy_travel.R

class Pins : AppCompatActivity(), OnMapReadyCallback {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pins)


        findViewById<MaterialButton>(R.id.mark_places).setOnClickListener {

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            mapFragment?.getMapAsync(this)
        }

    }




    override fun onMapReady(googleMap: GoogleMap) {

        /**
        val mapView = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        mapView?.getMapAsync { googleMap ->

            googleMap.setOnMapClickListener { latLng ->

                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("My Marker")
                )
            }
        **/
        }








}