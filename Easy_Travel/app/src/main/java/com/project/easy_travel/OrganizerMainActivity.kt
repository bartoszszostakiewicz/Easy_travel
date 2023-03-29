package com.project.easy_travel

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.ViewModel.TripViewModel

class OrganizerMainActivity : AppCompatActivity() {

    var trip_data: Trip = Trip()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootActivity()
        //TODO: remove hardcode
        var trip_id = "-NMdAXK265B3ffD0kkYQ"
        val tripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        var loaded_data = tripViewModel.load(trip_id)

        //TODO: read data about a trip and set it
    }

    private fun rootActivity(){
        setContentView(R.layout.organizer_main)

        findViewById<Button>(R.id.button_edit_trip).setOnClickListener {
            editRoot()
        }


    }

    private fun editRoot(){
        setContentView(R.layout.edit_trip_root)

        findViewById<Button>(R.id.edit_trip_name).setOnClickListener {
            editName()
        }

        findViewById<Button>(R.id.edit_trip_return).setOnClickListener{
            rootActivity()
        }
    }

    private fun editName(){
        setContentView(R.layout.create_trip_page1)
        findViewById<TextView>(R.id.title_txt).text = "Modyfikacja wycieczki"
        var btn = findViewById<Button>(R.id.next_btn1)
        btn.text = "Zapisz"

        var trip_name = findViewById<EditText>(R.id.nameTrip_edttxt)
        var trip_description = findViewById<EditText>(R.id.describeTrip_edttxt)

        trip_name.setText(trip_data.title)
        trip_description.setText(trip_data.description)

        btn.setOnClickListener {
            trip_data = Trip(
                trip_name.text.toString(),
                trip_description.text.toString(),
                trip_data.tripPointsID,
                trip_data.organizerID,
                trip_data.guidesID,
                trip_data.participantsID
            )

            //TODO: update the database
            editRoot()
        }
    }
}