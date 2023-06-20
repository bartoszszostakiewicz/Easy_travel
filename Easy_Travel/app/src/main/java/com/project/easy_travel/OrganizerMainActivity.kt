package com.project.easy_travel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.InvitedUser
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.TripPoint
import com.project.easy_travel.ViewModel.*
import com.project.easy_travel.repository.MainRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class OrganizerMainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var trip_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trip_id = intent.getStringExtra("trip_id").toString()

        setContentView(R.layout.organizer_main)

        val application = applicationContext as MainApplication





        findViewById<ImageButton>(R.id.button_edit_trip).setOnClickListener {
            var intent = Intent(applicationContext, OrganizerEditActivity::class.java)
            intent.putExtra("trip_id", trip_id)
            startActivity(intent)
        }

        var main_map = findViewById<ConstraintLayout>(R.id.include_main_map)

        MenuActivity.LayoutSetup(this, main_map, application)

        val currUsrID = FirebaseAuth.getInstance().currentUser?.email.toString().replace(".", "_")
        application.tripViewModel.data.observe(this) {
            if (it.organizerID != currUsrID && !it.guidesID.contains(currUsrID)) {
                MenuActivity.priv_change_toast.show()
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        MenuActivity.OnMapReady(googleMap, this)
    }
}
