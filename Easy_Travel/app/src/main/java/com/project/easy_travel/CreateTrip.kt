package com.project.easy_travel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.*
import com.project.easy_travel.ViewModel.Pins

class CreateTrip : AppCompatActivity() {
    lateinit var nextButton1: Button
    lateinit var nextButton2: Button
    lateinit var nextButton3: Button

    lateinit var titleTxt: TextView

    lateinit var addTripPointBtn: Button
    lateinit var add_btn_tripPoint: Button
    lateinit var addMemberBtn: Button
    lateinit var cancel_btn_tripPoint: Button
    lateinit var map_btn_tripPoint: Button

    lateinit var nameTripEdttxt: EditText
    lateinit var describeTripEdttxt: EditText

    lateinit var recyclerViewTripPoint: RecyclerView
    lateinit var recyclerViewMember: RecyclerView

    //private lateinit var userViewModel: UserViewModel

    private lateinit var pointTripListActive: PointTripListActive
    private lateinit var memberListActive: MemberListActive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add a new xml file
        setContentView(R.layout.create_trip_page1)

        // inicializacja z pierwszego xmla
        nextButton1 = findViewById<Button>(R.id.next_btn1)
        nameTripEdttxt = findViewById<EditText>(R.id.nameTrip_edttxt) // Do bazy danych
        describeTripEdttxt = findViewById<EditText>(R.id.describeTrip_edttxt) // Do bazy danych

        val points = ArrayList<Point>() // Do bazy danych
        val guidesID = ArrayList<String>() // Do bazy danych
        val participantsID = ArrayList<String>() // Do bazy danych


        // Odwolanie do UserViewModel w pliku ViewModel/UserViewModel.kt
        //userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val pointTripListActiveItems = mutableListOf<TripPoint>()
        val memberListActiveItems = mutableListOf<InvitedUser>()


        nextButton1.setOnClickListener {
            Log.d("XXD1", nameTripEdttxt.text.toString())
            setContentView(R.layout.create_trip_page2)
            overridePendingTransition(R.anim.slide_right_to_left, R.anim.no_animation)

            nextButton2 = findViewById<Button>(R.id.next_btn2)
            addTripPointBtn = findViewById<Button>(R.id.addTripPoint_btn)
            recyclerViewTripPoint = findViewById<RecyclerView>(R.id.tripPoint_list)

            nextButton2.setOnClickListener {

                setContentView(R.layout.create_trip_page3)
                nextButton3 = findViewById<Button>(R.id.create_trip_btn)
                addMemberBtn = findViewById<Button>(R.id.add_member_btn)
                recyclerViewMember = findViewById<RecyclerView>(R.id.member_list)

                addMemberBtn.setOnClickListener {
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.dialog_add_member)

                    val tripPointName_edttxt = dialog.findViewById<EditText>(R.id.tripPointName_edttxt) // Do bazy danych
                    val tripPointDescribe_edttxt = dialog.findViewById<EditText>(R.id.tripPointDescribe_edttxt) // Do bazy danych

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



                nextButton3.setOnClickListener {
                    Log.d("XXD1", "Name trip - " + nameTripEdttxt.text.toString())
                    Log.d("XXD1", "Describe trip - " + describeTripEdttxt.text.toString())
                    Log.d("XXD1", "Points - " + points.toString())
                    Log.d("XXD1", "Guides - " + guidesID.toString())
                    Log.d("XXD1", "Participants - " + participantsID.toString())

                    var ref = FirebaseDatabase.getInstance().getReference("points")

                    // List of points
                    val pointsID = arrayListOf<String>()

                    // Add point to realtime database
                    for (point in points) {
                        val pointID = ref.push().key
                        pointsID.add(pointID.toString())
                        ref.child(pointID.toString()).setValue(point)
                    }

                    ref = FirebaseDatabase.getInstance().getReference("trips")
                    val organizerID = FirebaseAuth.getInstance().currentUser?.email.toString() // adres e-mail organizatora

                    // Add trip to realtime database
                    val tripID = ref.push().key
                    val trip = Trip(nameTripEdttxt.text.toString(), describeTripEdttxt.text.toString(), pointsID, replaceDotsWithEmail(organizerID), guidesID, participantsID)
                    ref.child(tripID.toString()).setValue(trip)

                    val intent = Intent(this, OrganizerMainActivity::class.java)
                    startActivity(intent)
                }
            }

            addTripPointBtn.setOnClickListener {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_create_trip_point)

                map_btn_tripPoint = dialog.findViewById<Button>(R.id.map_btn)
                add_btn_tripPoint = dialog.findViewById<Button>(R.id.add_btn)
                cancel_btn_tripPoint = dialog.findViewById<Button>(R.id.back_btn)

                map_btn_tripPoint.setOnClickListener {
                    startActivity(Intent(this, Pins::class.java))
                }


                add_btn_tripPoint.setOnClickListener {
                    val name = dialog.findViewById<EditText>(R.id.tripPointName_edttxt).text.toString()
                    val description = dialog.findViewById<EditText>(R.id.tripPointDescribe_edttxt).text.toString()

                    val tripPoint = TripPoint(name, description)
                    points.add(Point(name, description, 0.0, 0.0))


                    pointTripListActiveItems.add(tripPoint)
                    pointTripListActive = PointTripListActive(pointTripListActiveItems)

                    recyclerViewTripPoint.adapter = pointTripListActive
                    recyclerViewTripPoint.layoutManager = LinearLayoutManager(this)
                    dialog.dismiss()
                }
                cancel_btn_tripPoint.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }



    }
}

fun replaceDotsWithEmail(email: String): String {
    return email.replace(".", "_")
}

class PointTripListActive (
    private val tripPoints: MutableList<TripPoint>
) : RecyclerView.Adapter<PointTripListActive.TripPointViewHolder>() {
    class TripPointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripPointViewHolder {
        return TripPointViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.point_trip_list_element,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return tripPoints.size
    }

    override fun onBindViewHolder(holder: TripPointViewHolder, position: Int) {
        val curTripPoint = tripPoints[position]
        holder.itemView.apply {
            var tripPointName = findViewById<TextView>(R.id.point_trip_element_title_txt)
            var tripPointDescribe = findViewById<TextView>(R.id.point_trip_element_describe_txt)
            tripPointName.text = curTripPoint.title
            tripPointDescribe.text = curTripPoint.description
        }
    }


}

class MemberListActive (
    private val members: MutableList<InvitedUser>,
) : RecyclerView.Adapter<MemberListActive.MemberViewHolder>() {
    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.member_list_element,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return members.size
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val curMember = members[position]
        holder.itemView.apply {
            var memberName = findViewById<TextView>(R.id.member_element_email_txt)
            var memberRole = findViewById<TextView>(R.id.member_element_role_txt)
            memberName.text = curMember.email
            memberRole.text = curMember.role
        }
    }
}