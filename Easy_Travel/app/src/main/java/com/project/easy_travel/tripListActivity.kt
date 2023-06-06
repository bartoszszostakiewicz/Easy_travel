package com.project.easy_travel


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.remote.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.TripCell
import com.project.easy_travel.ViewModel.Chat_Activity_B
import com.project.easy_travel.ViewModel.LoginActivity
import com.project.easy_travel.ViewModel.MainViewModel
import com.project.easy_travel.ViewModel.TripViewModel


class TripListActivity : AppCompatActivity() {
    private lateinit var tripActive: TripActive // RecyclerView adapter

    // Firebase auth
    private lateinit var mAuth: FirebaseAuth
    private lateinit var authUser: String

    // View elements
    private val tripItems: MutableList<Trip> = mutableListOf()

    // View models
    private lateinit var userViewModel: UserViewModel
    private lateinit var tripViewModel: TripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_list)

        val application = applicationContext as MainApplication

        // --- view models ---
        userViewModel = application.userViewModel //ViewModelProvider(this).get(UserViewModel::class.java)
        tripViewModel = application.tripViewModel //ViewModelProvider(this).get(TripViewModel::class.java)

        // --- firebase auth ---
        mAuth = FirebaseAuth.getInstance()
        authUser = mAuth.currentUser?.email.toString()
        val authUserWithoutDot = replaceDotsWithEmail(authUser)

        // --- view elements ---
        val helloTxt = findViewById<TextView>(R.id.hello_txt)
        val tripList = findViewById<RecyclerView>(R.id.tripList)
        val backBtn = findViewById<ImageView>(R.id.logOutButton)

        // --- view elements setup ---
        tripActive = TripActive(tripItems, Intent(applicationContext, MenuActivity::class.java), R.layout.trip_list_element, tripViewModel)
        tripList.adapter = tripActive
        tripList.layoutManager = LinearLayoutManager(this)

        // --- observers ---
        userViewModel.getById(authUserWithoutDot).observe(this, Observer { user ->
            if (user != null) {
                helloTxt.text = "Witam ${user.name} ${user.surname}"
            } else {
                helloTxt.text = "Witam użytkowniku!"
            }
        })

        tripViewModel.getAllItems().observe(this, Observer { trips ->
            val tripList = mutableListOf<Trip>()
            for (trip in trips) {
                if (trip.guidesID.contains(authUserWithoutDot) || trip.participantsID.contains(authUserWithoutDot) || trip.organizerID == authUserWithoutDot) {
                    tripList.add(trip)
                }
            }
            tripItems.clear()
            tripItems.addAll(tripList)

            this.tripActive.notifyDataSetChanged()
        })




        backBtn.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        findViewById<Button>(R.id.create_trip).setOnClickListener {
            startActivity(Intent(applicationContext, CreateTrip::class.java))
        }

        findViewById<Button>(R.id.join_trip).setOnClickListener {
        }


    }
    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }
}