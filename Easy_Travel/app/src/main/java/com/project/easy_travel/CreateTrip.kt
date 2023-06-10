package com.project.easy_travel

import android.app.DatePickerDialog
import androidx.lifecycle.Observer
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.Model.*
import com.project.easy_travel.ViewModel.Pins
import com.project.easy_travel.ViewModel.TripPointViewModel
import com.project.easy_travel.ViewModel.TripViewModel
import com.project.easy_travel.remote.UserViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateTrip : AppCompatActivity() {
    lateinit var application: MainApplication
    // View elements from xml #1
    lateinit var nextButton1: Button
    lateinit var nameTripEdttxt: EditText
    lateinit var describeTripEdttxt: EditText
    lateinit var dateTimePicker: EditText

    // View elements from xml #2
    lateinit var nextButton2: Button
    lateinit var addTripPointBtn: Button
    lateinit var add_btn_tripPoint: Button
    lateinit var date_start_tripPoint: EditText
    lateinit var date_finish_tripPoint: EditText

    lateinit var cancel_btn_tripPoint: Button
    lateinit var map_btn_tripPoint: Button

    // View elements from xml #3
    lateinit var nextButton3: Button
    lateinit var addMemberBtn: Button
    private lateinit var pointTripListActive: PointTripListActive
    private lateinit var memberListActive: MemberListActive

    lateinit var titleTxt: TextView

    lateinit var recyclerViewTripPoint: RecyclerView
    lateinit var recyclerViewMember: RecyclerView

    // View models
    private lateinit var userViewModel: UserViewModel
    private lateinit var tripViewModel: TripViewModel
    private lateinit var tripPointViewModel: TripPointViewModel

    //private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        application = applicationContext as MainApplication

        userViewModel = application.userViewModel //ViewModelProvider(this).get(UserViewModel::class.java)
        tripViewModel = application.tripViewModel //ViewModelProvider(this).get(TripViewModel::class.java)
        tripPointViewModel = application.tripPointViewModel //ViewModelProvider(this).get(TripPointViewModel::class.java)

        // Add a xml #1
        setContentView(R.layout.create_trip_page1)

        val points = ArrayList<Point>() // Do bazy danych
        val guidesID = ArrayList<String>() // Do bazy danych
        val participantsID = ArrayList<String>() // Do bazy danych

        // Initialize view elements from xml #1
        nextButton1 = findViewById<Button>(R.id.next_btn1)
        nameTripEdttxt = findViewById<EditText>(R.id.nameTrip_edttxt) // Do bazy danych
        describeTripEdttxt = findViewById<EditText>(R.id.describeTrip_edttxt) // Do bazy danych
        dateTimePicker = findViewById<EditText>(R.id.date_picker_actions)


        val pointTripListActiveItems = mutableListOf<Point>()
        val memberListActiveItems = mutableListOf<InvitedUser>()


        dateTimePicker.setOnClickListener {
            setDateTime(dateTimePicker, this)
        }

        // This button triggers the transition to the xml #2
        nextButton1.setOnClickListener {
            // Validate data
            if (nameTripEdttxt.text.toString().isEmpty()) {
                nameTripEdttxt.error = "Wprowadź nazwę wycieczki"
                nameTripEdttxt.requestFocus()
                return@setOnClickListener
            }

            if (describeTripEdttxt.text.toString().isEmpty()) {
                describeTripEdttxt.error = "Wprowadź opis wycieczki"
                describeTripEdttxt.requestFocus()
                return@setOnClickListener
            }

            if (dateTimePicker.text.toString().isEmpty()) {
                dateTimePicker.error = "Wprowadź datę rozpoczęcia wycieczki"
                dateTimePicker.requestFocus()
                return@setOnClickListener
            }

            // Add a xml #2
            setContentView(R.layout.create_trip_page2)
            overridePendingTransition(R.anim.slide_right_to_left, R.anim.no_animation)

            // Initialize view elements from xml #2
            nextButton2 = findViewById<Button>(R.id.next_btn2)
            addTripPointBtn = findViewById<Button>(R.id.addTripPoint_btn)
            recyclerViewTripPoint = findViewById<RecyclerView>(R.id.tripPoint_list)

            // This button triggers the transition to the xml #3
            nextButton2.setOnClickListener {
                setContentView(R.layout.create_trip_page3)

                // Initialize view elements from xml #3
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


                // Final button to create a trip and add it to the database
                nextButton3.setOnClickListener {

                    // List of points
                    val pointsID = arrayListOf<String>()

                    // Add point to realtime database
                    for (point in points) {
                        tripPointViewModel.save(point)



                        Log.d("pointID", point.id.toString())
                        pointsID.add(point.id.toString())
                    }

                    val organizerID = FirebaseAuth.getInstance().currentUser?.email.toString() // adres e-mail organizatora

                    // Add trip to realtime database
                    val trip = Trip(id = "",
                                    title = nameTripEdttxt.text.toString(),
                                    description = describeTripEdttxt.text.toString(),
                                    tripPointsID = pointsID,
                                    organizerID = replaceDotsWithEmail(organizerID),
                                    guidesID = guidesID,
                                    participantsID = participantsID,
                                    startDate = convertStringToTimestamp(dateTimePicker.text.toString()))
                    var trip_id = tripViewModel.save(trip)
                    tripViewModel.setData(trip)

                    val intent = Intent(this, OrganizerMainActivity::class.java)
                    intent.putExtra("trip_id", trip_id)
                    startActivity(intent)
                    this.finish()
                }
            }

            addTripPointBtn.setOnClickListener {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_create_trip_point)

                map_btn_tripPoint = dialog.findViewById<Button>(R.id.map_btn)
                add_btn_tripPoint = dialog.findViewById<Button>(R.id.add_btn)
                cancel_btn_tripPoint = dialog.findViewById<Button>(R.id.back_btn)

                date_start_tripPoint = dialog.findViewById<EditText>(R.id.date_picker_start_actions)
                date_finish_tripPoint = dialog.findViewById<EditText>(R.id.date_picker_finish_actions)

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

                    tripPointViewModel.data.observe(this, androidx.lifecycle.Observer { point ->

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
        }



    }
}


fun convertStringToTimestamp(strDate: String): Long {
    try {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = formatter.parse(strDate)
        val timestamp = date.time
        return timestamp
    } catch (e: Exception) {
        return 0L
    }
}

fun setDateTime(dateTimePicker: EditText, context: Context) {
    val currentDateTime = Calendar.getInstance()
    val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        val time = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            currentDateTime.set(Calendar.YEAR, year)
            currentDateTime.set(Calendar.MONTH, monthOfYear)
            currentDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            currentDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            currentDateTime.set(Calendar.MINUTE, minute)
            dateTimePicker.setText(SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentDateTime.time))
        }
        val currentTime = Calendar.getInstance()
        TimePickerDialog(
            context,
            time,
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        ).show()
    }
    DatePickerDialog(
        context,
        date,
        currentDateTime.get(Calendar.YEAR),
        currentDateTime.get(Calendar.MONTH),
        currentDateTime.get(Calendar.DAY_OF_MONTH)
    ).show()


}


fun replaceDotsWithEmail(email: String): String {
    return email.replace(".", "_")
}

class PointTripListActive(
    private val tripPoints: MutableList<Point>,
    private val tripPointViewModel: TripPointViewModel
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
            var editButton = findViewById<Button>(R.id.change_btn)
            tripPointName.text = curTripPoint.name
            tripPointDescribe.text = curTripPoint.describe
            editButton.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialog_create_trip_point)


                val tripTitle = dialog.findViewById<TextView>(R.id.tripPointName_edttxt)
                val tripDescribe = dialog.findViewById<TextView>(R.id.tripPointDescribe_edttxt)


                tripTitle.text = Editable.Factory.getInstance().newEditable(curTripPoint.name)
                tripDescribe.text = Editable.Factory.getInstance().newEditable(curTripPoint.describe)

                val date_start_tripPoint = dialog.findViewById<EditText>(R.id.date_picker_start_actions)
                val date_finish_tripPoint = dialog.findViewById<EditText>(R.id.date_picker_finish_actions)

                date_start_tripPoint.setText(
                    if (curTripPoint.startDate == 0L) {
                        ""
                    } else {
                        SimpleDateFormat(
                            "yyyy-MM-dd HH:mm",
                            Locale.getDefault()
                        ).format(curTripPoint.startDate)
                    }
                )

                date_finish_tripPoint.setText(
                    if (curTripPoint.finishDate == 0L) {
                        ""
                    } else {
                        SimpleDateFormat(
                            "yyyy-MM-dd HH:mm",
                            Locale.getDefault()
                        ).format(curTripPoint.finishDate)
                    }
                )

                date_start_tripPoint.setOnClickListener {
                    setDateTime(date_start_tripPoint, context)
                }

                date_finish_tripPoint.setOnClickListener {
                    setDateTime(date_finish_tripPoint, context)
                }

                val deleteButton = dialog.findViewById<Button>(R.id.back_btn)
                deleteButton.text = "Usuń"

                 deleteButton.setOnClickListener {
                    //delete current trip point
                    tripPoints.removeAt(position)
                    notifyDataSetChanged()
                    dialog.dismiss()
                }

                val updateButton = dialog.findViewById<Button>(R.id.add_btn)
                updateButton.text = "Zaktualizuj"

                updateButton.setOnClickListener {
                    // Validate data
                    if (tripTitle.text.toString().isEmpty()) {
                        tripTitle.error = "Wprowadź nazwę punktu"
                        tripTitle.requestFocus()
                        return@setOnClickListener
                    }

                    //update current trip point
                    val name =
                        tripTitle.text.toString()
                    val description =
                        tripDescribe.text.toString()

                    var lat = 0.0
                    var lng = 0.0

                    tripPointViewModel.data.observe((context as FragmentActivity), androidx.lifecycle.Observer { point ->

                        lat = point.lat
                        lng = point.lng

                    })

                    val startDate = convertStringToTimestamp(
                        dialog.findViewById<EditText>(R.id.date_picker_start_actions).text.toString()
                    )

                    val finishDate = convertStringToTimestamp(
                        dialog.findViewById<EditText>(R.id.date_picker_finish_actions).text.toString()
                    )

                    val point = Point(curTripPoint.id, name, description, lat, lng, startDate, finishDate)
                    tripPoints[position] = point
                    notifyItemChanged(position)
                    dialog.dismiss()
                }


                dialog.show()
            }
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
            val editButton = findViewById<Button>(R.id.change_btn)
            memberName.text = curMember.email
            memberRole.text = curMember.role

            editButton.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialog_add_member)

                val memberEmail = dialog.findViewById<TextView>(R.id.emailMember_edttxt)
                val memberRole = dialog.findViewById<Spinner>(R.id.role_spinner)
                val roles = resources.getStringArray(R.array.roles)
                memberRole.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, roles)

                memberEmail.text = Editable.Factory.getInstance().newEditable(curMember.email)
                // TODO: Set role in spinner



                val deleteButton = dialog.findViewById<Button>(R.id.back_btn)
                deleteButton.text = "Usuń"

                deleteButton.setOnClickListener {
                    //delete current member
                    members.removeAt(position)
                    notifyDataSetChanged()
                    dialog.dismiss()
                }

                val updateButton = dialog.findViewById<Button>(R.id.add_btn)
                updateButton.text = "Zaktualizuj"

                updateButton.setOnClickListener {
                    // Validate data
                    if (memberEmail.text.toString().isEmpty()) {
                        memberEmail.error = "Wprowadź email członka"
                        memberEmail.requestFocus()
                        return@setOnClickListener
                    }

                    //update current member
                    val email =
                        memberEmail.text.toString()
                    val role =
                        memberRole.selectedItem.toString()

                    val member = InvitedUser(email, role)
                    members[position] = member
                    notifyItemChanged(position)
                    dialog.dismiss()
                }

                dialog.show()
            }


        }
    }
}