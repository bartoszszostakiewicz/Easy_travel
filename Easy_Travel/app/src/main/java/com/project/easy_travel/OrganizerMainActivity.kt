package com.project.easy_travel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.InvitedUser
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.TripPoint
import com.project.easy_travel.ViewModel.Pins
import com.project.easy_travel.ViewModel.TripViewModel

class OrganizerMainActivity : AppCompatActivity() {

    lateinit var trip_data: DataSnapshot

    lateinit var pointTripListActiveItems: MutableList<TripPoint>
    lateinit var memberListActiveItems: MutableList<InvitedUser>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootActivity()
        //TODO: remove hardcode
        var trip_id = "-NMdAXK265B3ffD0kkYQ"
        //val tripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        //var loaded_data = tripViewModel.load(trip_id)

        FirebaseDatabase.getInstance().getReference("trips").get().addOnCompleteListener {
            trip_data = it.result
        }

        pointTripListActiveItems = mutableListOf<TripPoint>()
        memberListActiveItems =  mutableListOf<InvitedUser>()

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

        findViewById<Button>(R.id.edit_trip_events).setOnClickListener {
            editTripPoints()
        }

        findViewById<Button>(R.id.edit_trip_members).setOnClickListener {
            editParticipants()
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
        /*
        trip_name.setText(trip_data.title)
        trip_description.setText(trip_data.description)
*/
        btn.setOnClickListener {
            /*
            trip_data = Trip(
                trip_name.text.toString(),
                trip_description.text.toString(),
                trip_data.tripPointsID,
                trip_data.organizerID,
                trip_data.guidesID,
                trip_data.participantsID
            )
*/
            //TODO: update the database
            editRoot()
        }
    }

    private fun editTripPoints()
    {
        setContentView(R.layout.create_trip_page2)
        findViewById<TextView>(R.id.title_txt).text = "Modyfikacja wycieczki"

        var ret_btn = findViewById<Button>(R.id.next_btn2)
        ret_btn.text = "Zapisz"


        var recyclerViewTripPoint = findViewById<RecyclerView>(R.id.tripPoint_list)
        var addTripPointBtn = findViewById<Button>(R.id.addTripPoint_btn)
        addTripPointBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_create_trip_point)

            var map_btn_tripPoint = dialog.findViewById<Button>(R.id.map_btn)
            var add_btn_tripPoint = dialog.findViewById<Button>(R.id.add_btn)
            var cancel_btn_tripPoint = dialog.findViewById<Button>(R.id.back_btn)

            map_btn_tripPoint.setOnClickListener {
                startActivity(Intent(this, Pins::class.java))
            }


            add_btn_tripPoint.setOnClickListener {
                val name = dialog.findViewById<EditText>(R.id.tripPointName_edttxt).text.toString()
                val description = dialog.findViewById<EditText>(R.id.tripPointDescribe_edttxt).text.toString()

                val tripPoint = TripPoint(name, description)


                pointTripListActiveItems.add(tripPoint)
                var pointTripListActive = PointTripListActive(pointTripListActiveItems)

                recyclerViewTripPoint.adapter = pointTripListActive
                recyclerViewTripPoint.layoutManager = LinearLayoutManager(this)
                dialog.dismiss()
            }
            cancel_btn_tripPoint.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        ret_btn.setOnClickListener {
            editRoot()
        }
    }

    private fun editParticipants() {
        setContentView(R.layout.create_trip_page3)
        var addMemberBtn = findViewById<Button>(R.id.add_member_btn)
        var recyclerViewMember = findViewById<RecyclerView>(R.id.member_list)

        var ret_btn = findViewById<Button>(R.id.create_trip_btn)
        ret_btn.text = "Zapisz"

        addMemberBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_add_member)

            val roles = resources.getStringArray(R.array.roles)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val role_spinner = dialog.findViewById<Spinner>(R.id.role_spinner)
            role_spinner.adapter = adapter

            val add_btn = dialog.findViewById<Button>(R.id.add_btn)
            val cancel_btn = dialog.findViewById<Button>(R.id.back_btn)

            add_btn.setOnClickListener {
                val email = dialog.findViewById<EditText>(R.id.emailMember_edttxt).text.toString()
                val role = role_spinner.selectedItem.toString()

                memberListActiveItems.add(InvitedUser(email, role))
                var memberListActive = MemberListActive(memberListActiveItems)

                recyclerViewMember.adapter = memberListActive
                recyclerViewMember.layoutManager = LinearLayoutManager(this)
                dialog.dismiss()
            }
            cancel_btn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        ret_btn.setOnClickListener {
            editRoot()
        }
    }
}