package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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



class MenuActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var application: MainApplication

    private lateinit var tripViewModel: TripViewModel
    private lateinit var tripPointViewModel: TripPointViewModel

    private lateinit var tripID: String

    private val listPoints = mutableListOf<Point>()
    private var pointsId : List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)

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

                        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
                        mapFragment?.getMapAsync(this@MenuActivity)
                    }
                })

            }
        })





//        /*****************************************************************************************************************************************************************/
//        // Read points from databasa
//        val db = Firebase.database.reference.child("points")
//        db.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (pointSnapshot in dataSnapshot.children) {
//                    val pointKey = pointSnapshot.key.toString()
//                    val point = pointSnapshot.getValue(Point::class.java)
//                    point?.let {
//                        if (pointKey in pointsId)
//                            listPoints.add(point)
//                    }
//                }
//
//                val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
//                mapFragment?.getMapAsync(this@MenuActivity)
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.d("points", "loadPost:onCancelled", databaseError.toException())
//            }
//        })
//        /*****************************************************************************************************************************************************************/



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

        listPoints.forEach { point ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(point.toLatLng())
                    .title(point.name)
            )
        }

        // Dodaj listener do kliknięcia na pinezkę
        googleMap.setOnMarkerClickListener { marker ->
            // Pobierz opis pinezki
            val description = marker.title

            // Wyświetl opis w dowolny sposób, np. w oknie dialogowym
            AlertDialog.Builder(this)
                .setTitle("Opis")
                .setMessage(description)
                .setPositiveButton("OK", null)
                .show()

            // Zwróć true, aby oznaczyć kliknięcie jako obsłużone
            true
        }
    }




}
