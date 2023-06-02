package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.User
import com.project.easy_travel.ViewModel.TripPointViewModel
import java.text.SimpleDateFormat
import java.util.*

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information_activity)

        val application = applicationContext as MainApplication

        val userViewModel = application.userViewModel
        val tripViewModel = application.tripViewModel

        val members = mutableListOf<Member>()
        val memberRecyclerView = findViewById<RecyclerView>(R.id.members_recycler_view)
        val memberAdapter = MemberAdapter(members, R.layout.information_member_element)
        memberRecyclerView.adapter = memberAdapter
        memberRecyclerView.layoutManager = LinearLayoutManager(this)

        val trip_name = findViewById<TextView>(R.id.tripName_txt)
        val trip_date = findViewById<TextView>(R.id.dateStart_txt)
        val trip_description = findViewById<TextView>(R.id.describeTrip_txt)

        val tripPlanButton = findViewById<Button>(R.id.tripPlan_button)
        val backButton = findViewById<Button>(R.id.back_button)

        tripViewModel.data.observe(this, Observer { trip ->
            members.clear()

            trip_name.text = "Nazwa wycieczki: ${trip.title}"
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val dateText = "Data rozpoczęcia: " + sdf.format(trip.startDate * 1000).toString()
            trip_date.text = dateText
            trip_description.text = trip.description

            userViewModel.getById(trip.organizerID).observe(this, Observer { user ->
                if (user != null) {
                    members.add(Member(user.name+" "+user.surname, "Organizator"))
                    memberAdapter.notifyDataSetChanged()
                }
            })

            for (i in trip.guidesID.indices) {
                userViewModel.getById(trip.guidesID[i]).observe(this, Observer { user ->
                    if (user != null) {
                        members.add(Member(user.name+" "+user.surname, "Przewodnik"))
                        memberAdapter.notifyDataSetChanged()
                    }
                })
            }
            for (i in trip.participantsID.indices) {
                userViewModel.getById(trip.participantsID[i]).observe(this, Observer { user ->
                    if (user != null) {
                        members.add(Member(user.name+" "+user.surname, "Uczestnik"))
                        memberAdapter.notifyDataSetChanged()
                    }
                })
            }

            memberAdapter.notifyDataSetChanged()

        })

        tripPlanButton.setOnClickListener{
            val intent = Intent(this, TripListPointActivity::class.java)
            startActivity(intent)
        }

        backButton.setOnClickListener{
            this.finish()
        }

    }
}

data class Member(
    val name: String,
    val role: String
)

class MemberAdapter (
    private val users: MutableList<Member>,
    private val xmlFile: Int
) : RecyclerView.Adapter<MemberAdapter.TripViewHolder>()//AppCompatActivity()
{
    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): TripViewHolder {
        return TripViewHolder(
            LayoutInflater.from(parent.context).inflate(
                xmlFile,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val curUsers = users[position]


        holder.itemView.apply {
            var name_text = findViewById<TextView>(R.id.nameTitle_txt)
            var role_text = findViewById<TextView>(R.id.memberTitle_txt)

            name_text.text = curUsers.name
            role_text.text = curUsers.role

        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}