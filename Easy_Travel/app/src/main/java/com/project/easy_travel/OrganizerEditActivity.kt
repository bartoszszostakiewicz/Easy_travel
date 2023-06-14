package com.project.easy_travel

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.InvitedUser
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.ViewModel.Pins
import com.project.easy_travel.ViewModel.TripPointViewModel
import com.project.easy_travel.ViewModel.TripViewModel
import com.project.easy_travel.repository.TripPointRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.Dispatchers.Unconfined
import com.google.firebase.auth.FirebaseAuth

class OrganizerEditActivity : AppCompatActivity() {

    lateinit var application: MainApplication
    private lateinit var tripViewModel: TripViewModel
    private lateinit var tripPointViewModel: TripPointViewModel

    lateinit var trip_id : String

    //var trip_data: MutableMap<String, Any>? = null
    var trip_data: Trip? = null

    lateinit var pointTripListActiveItems: MutableList<Point>
    lateinit var memberListActiveItems: MutableList<InvitedUser>

    lateinit var trip_fb_instance : DatabaseReference
    lateinit var points_fb_instance : DatabaseReference
    private lateinit var memberListActive: MemberListActive


    val TAG = "organizer/edit"

    private lateinit var pointTripListActive: PointTripListActive
    private lateinit var tripPointRep: TripPointRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trip_id = intent.getStringExtra("trip_id").toString()
        application = applicationContext as MainApplication

        var fb_inst = FirebaseDatabase.getInstance()
        trip_fb_instance = fb_inst.getReference("trips").child(trip_id)
        points_fb_instance = fb_inst.getReference("points")

        tripViewModel = application.tripViewModel

        tripPointViewModel = application.tripPointViewModel

        var trip_data_l = tripViewModel.getById(trip_id)

        Log.d(TAG, trip_data_l.value.toString())
        Log.d(TAG, trip_id)
        //var loaded_data = tripViewModel.load(trip_id)

        val userAuth = replaceDotsWithEmail(FirebaseAuth.getInstance().currentUser?.email.toString())

        tripViewModel.data.observe(this, Observer {trip ->
            if (trip.organizerID == userAuth) {
                findViewById<Button>(R.id.del_trip).isEnabled = true
                findViewById<Button>(R.id.del_trip).alpha = 1f
            }
        })

        //TODO: change read data to complete model when ready
        trip_fb_instance.get().addOnCompleteListener {
            trip_data = it.result.getValue(Trip::class.java)
            Log.d("read success", trip_data.toString())
        }.addOnFailureListener {
            Log.e("read error", it.toString())
        }

        //pointTripListActiveItems = mutableListOf<Trip>()
        memberListActiveItems =  mutableListOf<InvitedUser>()

        tripPointRep = TripPointRepository.getInstance()
        editRoot()
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
            this.finish()
        }

        findViewById<Button>(R.id.del_trip).setOnClickListener{
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_confirm)

            val yes_btn = dialog.findViewById<Button>(R.id.yes_btn)
            val no_btn = dialog.findViewById<Button>(R.id.no_btn)

            yes_btn.setOnClickListener {
                deleteTrip()
                intent = Intent(this, TripListActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
            no_btn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun deleteTrip(){
        tripViewModel.data.observe(this, Observer {trip ->

            // points in trip.points
            for (point in trip.tripPointsID) {
                tripPointViewModel.getById(point).observe(this, Observer {point ->
                    if (point != null) {
                        tripPointViewModel.delete(point.id)
                    }
                })
            }

            tripViewModel.delete(trip.id)
        })
    }

    private fun editName(){
        setContentView(R.layout.create_trip_page1)
        findViewById<TextView>(R.id.title_txt).text = "Modyfikacja wycieczki"
        var btn = findViewById<Button>(R.id.next_btn1)
        btn.text = "Zapisz"

        var trip_name = findViewById<EditText>(R.id.nameTrip_edttxt)
        var trip_description = findViewById<EditText>(R.id.describeTrip_edttxt)
        var dateTimePicker = findViewById<EditText>(R.id.date_picker_actions)
        dateTimePicker.setOnClickListener {
            setDateTime(dateTimePicker, this)
        }


        trip_name.setText(trip_data!!.title)
        trip_description.setText(trip_data!!.description)

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = Date(trip_data!!.startDate)
        dateTimePicker.setText(formatter.format(date))


        btn.setOnClickListener {

            trip_data!!.title = trip_name.text.toString()
            trip_data!!.description = trip_description.text.toString()
            trip_data!!.startDate = (formatter.parse(dateTimePicker.text.toString())?.time!!)

            Log.d("update success", trip_data.toString())

            trip_fb_instance.setValue(trip_data!!.toMap()).addOnFailureListener {
                Log.e("update error", it.toString())
            }.addOnSuccessListener {
                tripViewModel.setData(trip_data!!)
            }

            editRoot()
        }
    }

    private fun editTripPoints()
    {
        setContentView(R.layout.create_trip_page2)

        val points = ArrayList<Point>()
        pointTripListActiveItems = mutableListOf<Point>()
        var recyclerViewTripPoint = findViewById<RecyclerView>(R.id.tripPoint_list)

        var pointIDs = trip_data!!.tripPointsID
        for (id in pointIDs)
        {
            points_fb_instance.child(id).get().addOnSuccessListener {
                var point = it.getValue(Point::class.java)!!
                point.id = id
                pointTripListActiveItems.add(point)
                pointTripListActive = PointTripListActive(pointTripListActiveItems, tripPointViewModel)

                recyclerViewTripPoint.adapter = pointTripListActive
                recyclerViewTripPoint.layoutManager = LinearLayoutManager(this)

            }.addOnFailureListener {
                Log.e(TAG, "not loaded point")
            }
        }

        findViewById<TextView>(R.id.title_txt).text = "Modyfikacja wycieczki"

        var ret_btn = findViewById<Button>(R.id.next_btn2)

        ret_btn.text = "Zapisz"

        var addTripPointBtn = findViewById<Button>(R.id.addTripPoint_btn)

        addTripPointBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_create_trip_point)

            var map_btn_tripPoint = dialog.findViewById<Button>(R.id.map_btn)
            var add_btn_tripPoint = dialog.findViewById<Button>(R.id.add_btn)
            var cancel_btn_tripPoint = dialog.findViewById<Button>(R.id.back_btn)

            var date_start_tripPoint = dialog.findViewById<EditText>(R.id.date_picker_start_actions)
            var date_finish_tripPoint = dialog.findViewById<EditText>(R.id.date_picker_finish_actions)

            date_start_tripPoint.setOnClickListener {
                setDateTime(date_start_tripPoint, this)
            }

            date_finish_tripPoint.setOnClickListener {
                setDateTime(date_finish_tripPoint, this)
            }


            map_btn_tripPoint.setOnClickListener {

                val intent = Intent(this, Pins::class.java)
                startActivity(intent)
            }


            add_btn_tripPoint.setOnClickListener {
                // Validate data
                if (dialog.findViewById<EditText>(R.id.tripPointName_edttxt).text.toString().isEmpty()) {
                    dialog.findViewById<EditText>(R.id.tripPointName_edttxt).error = "Wpisz nazwę punktu"
                    return@setOnClickListener
                }

                val name = dialog.findViewById<EditText>(R.id.tripPointName_edttxt).text.toString()
                val description = dialog.findViewById<EditText>(R.id.tripPointDescribe_edttxt).text.toString()

                var lat = 0.0
                var lng = 0.0

                tripPointViewModel.data.observe(this, Observer { point ->

                    lat = point.lat
                    lng = point.lng

                    Log.d("XXD1", "Lat - " + lat.toString())
                    Log.d("XXD1", "Lng - " + lng.toString())


                })


                val tripPoint = Point("", name, description, lat, lng, convertStringToTimestamp(date_start_tripPoint.text.toString()), convertStringToTimestamp(date_finish_tripPoint.text.toString()))
                points.add(tripPoint)


                pointTripListActiveItems.add(tripPoint)
                pointTripListActive = PointTripListActive(pointTripListActiveItems, tripPointViewModel)

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
            for (p in pointTripListActiveItems)
                if(p.id.isEmpty())
                    tripPointViewModel.save(p, p.id)

            var res: List<String> = pointTripListActiveItems.map { point -> point.id }

            for (id in pointIDs)
                if(!res.contains(id))
                    tripPointViewModel.delete(id)
                else
                {
                    var point = pointTripListActiveItems.find { it.id == id }
                    points_fb_instance.child(id).setValue(point!!.toMap()).addOnFailureListener {
                        Log.e(TAG, "not saved point")
                    }
                }


            trip_data!!.tripPointsID = res
            trip_fb_instance.setValue(trip_data!!.toMap()).addOnFailureListener {
                Log.e("update error", it.toString())
            }.addOnSuccessListener {
                tripViewModel.setData(trip_data!!)
            }
            editRoot()
        }
    }

    private fun editParticipants() {
        setContentView(R.layout.create_trip_page3)

        var addMemberBtn = findViewById<Button>(R.id.add_member_btn)
        var recyclerViewMember = findViewById<RecyclerView>(R.id.member_list)

        val guidesID = ArrayList<String>() // Do bazy danych
        val participantsID = ArrayList<String>() // Do bazy danych
        val memberListActiveItems = mutableListOf<InvitedUser>()
        val roles = resources.getStringArray(R.array.roles)

        for(guide in trip_data!!.guidesID)
        {
            //participantsID.add(guide)
            memberListActiveItems.add(InvitedUser(guide.replace('_', '.'), roles[1]))
        }

        for(participant in trip_data!!.participantsID)
        {
            //participantsID.add(participant)
            memberListActiveItems.add(InvitedUser(participant.replace('_', '.'), roles[0]))
        }

        memberListActive = MemberListActive(memberListActiveItems)

        recyclerViewMember.adapter = memberListActive
        recyclerViewMember.layoutManager = LinearLayoutManager(this)

        var ret_btn = findViewById<Button>(R.id.create_trip_btn)
        ret_btn.text = "Zapisz"


        addMemberBtn.setOnClickListener {

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_add_member)

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val role_spinner = dialog.findViewById<Spinner>(R.id.role_spinner)
            role_spinner.adapter = adapter

            val add_btn = dialog.findViewById<Button>(R.id.add_btn)
            val cancel_btn = dialog.findViewById<Button>(R.id.back_btn)

            add_btn.setOnClickListener {
                val emailEdtTxt = dialog.findViewById<EditText>(R.id.emailMember_edttxt)

                val email = dialog.findViewById<EditText>(R.id.emailMember_edttxt).text.toString()
                val role = role_spinner.selectedItem.toString()

                if (emailEdtTxt.text.toString().isEmpty()) {
                    emailEdtTxt.error = "Wprowadź adres email"
                    return@setOnClickListener
                }

                if (role == "Uczestnik") {
                    participantsID.add(replaceDotsWithEmail(email))
                } else {
                    guidesID.add(replaceDotsWithEmail(email))
                }

                memberListActiveItems.add(InvitedUser(email, role))
                memberListActive = MemberListActive(memberListActiveItems)

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

            for(p in memberListActiveItems)
            {
                val id = p.email.replace('.', '_')
                if(p.role == roles[1])//guide
                    guidesID.add(id)
                else
                    participantsID.add(id)
            }

            trip_data!!.participantsID = participantsID
            trip_data!!.guidesID = guidesID

            trip_fb_instance.setValue(trip_data!!.toMap()).addOnFailureListener {
                Log.e("update error", it.toString())
            }.addOnSuccessListener {
                tripViewModel.setData(trip_data!!)
            }
            editRoot()
        }
    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }
}