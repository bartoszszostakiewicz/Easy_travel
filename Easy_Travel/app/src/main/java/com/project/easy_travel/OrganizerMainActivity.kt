package com.project.easy_travel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.InvitedUser
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.TripPoint
import com.project.easy_travel.ViewModel.*
import com.project.easy_travel.repository.MainRepository
import java.util.*
import kotlin.math.log

//TODO: obesnie jest w cholere duplikacji kodu z clasy 'MenuActivity'
class OrganizerMainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var tripID: String
    private lateinit var tripPointViewModel: TripPointViewModel
    private lateinit var tripViewModel: TripViewModel
    lateinit var trip_id : String

    private val listPoints = mutableListOf<Point>()
    private var pointsId : List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trip_id = intent.getStringExtra("trip_id").toString()

        setContentView(R.layout.organizer_main)

        findViewById<Button>(R.id.button_edit_trip).setOnClickListener {
            var intent = Intent(applicationContext, OrganizerEditActivity::class.java)
            intent.putExtra("trip_id", trip_id)
            startActivity(intent)
        }

        val application = applicationContext as MainApplication

        tripViewModel = application.tripViewModel //ViewModelProvider(this).get(TripViewModel::class.java)
        tripPointViewModel = application.tripPointViewModel


        var main_map = findViewById<ConstraintLayout>(R.id.include_main_map)
        tripViewModel.data.observe(this, Observer {trip ->
            Toast.makeText(applicationContext, "Trip: ${trip.title}", Toast.LENGTH_SHORT).show()
            pointsId = trip.tripPointsID
            tripID = trip.id

            for (i in pointsId.indices) {
                tripPointViewModel.getById(pointsId[i]).observe(this, Observer { point ->
                    if (point != null) {
                        listPoints.add(point)

                        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
                        mapFragment?.getMapAsync(this@OrganizerMainActivity)
                    }
                })

            }
        })

        val tripBtn = main_map.findViewById<Button>(R.id.trip_button)
        val helpBtn = main_map.findViewById<Button>(R.id.help)
        val chatBtn = main_map.findViewById<Button>(R.id.chat)
        val infoBtn = main_map.findViewById<Button>(R.id.information)

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
        val dateList = mutableListOf<String>()

        listPoints.forEach { point ->

            val currentDate = Date()

            Log.d("test123",currentDate.toString())
            Log.d("test123",point.startDate.toString())

            googleMap.addMarker(
                MarkerOptions()
                    .position(point.toLatLng())
                    .title(point.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )

            titleList.add(point.name)
            descriptionList.add(point.describe)
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
            val description = descriptionList[i]
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton("Wróć", null)
                .show()

            true
        }

    }
}
