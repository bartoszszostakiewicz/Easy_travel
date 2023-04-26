package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.remote.UserViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.Model.TripCell
import com.project.easy_travel.ViewModel.Chat_Activity_B
import com.project.easy_travel.ViewModel.Organizacja
import com.project.easy_travel.ViewModel.TripViewModel
import com.project.easy_travel.ViewModel.UserViewModel
import kotlin.math.log


class TripListActivity : AppCompatActivity() {
    private lateinit var tripActive: TripActive

//    lateinit var userViewModel: UserViewModel
//    lateinit var tripViewModels: List<TripViewModel>

        //TODO: przy moich zmianach, ta linijk2 powoduje null-reference crashe
//    val mainApp = application as MainApp
//    val userViewModel = mainApp.userViewModelGet()
    lateinit var userViewModel: UserViewModel

    lateinit var tripViewModels: List<TripViewModel>
    private lateinit var mAuth: FirebaseAuth


    lateinit var helloTxt: TextView
    private val tripItems: MutableList<TripCell> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_list)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        mAuth = FirebaseAuth.getInstance()

        var tripList = findViewById<RecyclerView>(R.id.tripList)
        var backBtn = findViewById<Button>(R.id.logOutButton)

        helloTxt = findViewById(R.id.hello_txt)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        //userViewModel.load(intent.getStringExtra("userId").toString())
        userViewModel.load(replaceDotsWithEmail(mAuth.currentUser?.email.toString()))

        tripActive = TripActive(tripItems, this, Intent(applicationContext, MenuActivity::class.java), R.layout.trip_list_element)
        tripList.adapter = tripActive
        tripList.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.chat_test).setOnClickListener {
            startActivity(Intent(applicationContext,Chat_Activity_B::class.java))
        }

        backBtn.setOnClickListener {
            this.finish()
        }

        findViewById<Button>(R.id.create_trip).setOnClickListener {
            startActivity(Intent(applicationContext, CreateTrip::class.java))
        }

        findViewById<Button>(R.id.join_trip).setOnClickListener {
        }

//        userViewModel.user.observe(this) { user ->
//            Toast.makeText(this, user.tripsID.toString(), Toast.LENGTH_SHORT).show()
//            helloTxt.text = "Witam ${user.name} ${user.surname}"
//            for (trip in user.tripsID) {
//                Toast.makeText(this, trip, Toast.LENGTH_SHORT).show()
//                val tripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
//                tripViewModel.load(trip)
//                tripViewModel.trip.observe(this) { trip ->
//                    val tripCell = TripCell(trip.title, false, trip.description)
//                    if (!tripItems.contains(tripCell)) {
//                        tripItems.add(tripCell)
//                        tripActive.notifyDataSetChanged()
//                    }
//                }
//
//            }
//        }
    }
    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }
}