package com.project.easy_travel

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.ViewModel.TripViewModel
import java.text.SimpleDateFormat
import java.util.*


class TripActive (
    private val tripData: MutableList<Trip>,
    private val memberData: MutableList<String>,
    private val intent: Intent,
    private val intent2: Intent,
    private val xmlFile: Int,
    private val tripViewModel: TripViewModel
) : RecyclerView.Adapter<TripActive.TripViewHolder>()//AppCompatActivity()
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

//    fun addTripCell(tripCell: TripCell) {
//        tripCells.add(tripCell)
//        notifyItemInserted(tripCells.size - 1)
//    }

//    fun deleteTripCell() {
//        pass
//    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val dateTime = Date(timestamp)
        return dateFormat.format(dateTime)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val curTrip = tripData[position]
        val curMember = memberData[position]

        holder.itemView.apply {
            var detailButton = findViewById<ImageButton>(R.id.detailButton)
            var startDateText = findViewById<TextView>(R.id.startDateTitle)
            var tripTitle = findViewById<TextView>(R.id.tripTitle)
            if (curTrip.startDate == 0L) {
                startDateText.text = "Data rozpoczęcia: Nie zaznaczono"
            } else if (curTrip.startDate < Date().time) {
                startDateText.text = "Wycieczka w trakcie"
            } else {
                startDateText.text = "Data rozpoczęcia: ${convertTimestampToDate(curTrip.startDate)}"
            }

//            if (curMember == "organizer") {
//                val comp = tripTitle.compoundDrawables
//                val drEnd = comp[2]
//                drEnd?.setTint(Color.parseColor("#FF0000"))
//                tripTitle.setCompoundDrawables(comp[0], comp[1], drEnd, comp[3])
//            } else if (curMember == "guide") {
//                val comp = tripTitle.compoundDrawables
//                val drEnd = comp[2]
//                drEnd?.setTint(Color.parseColor("#0000FF"))
//                tripTitle.setCompoundDrawables(comp[0], comp[1], drEnd, comp[3])
//            }

            tripTitle.text = curTrip.title
            detailButton.setOnClickListener {
                tripViewModel.setData(curTrip)
                var currentUserID = FirebaseAuth.getInstance().currentUser?.email.toString().replace(".", "_")
                if(currentUserID == curTrip.organizerID || curTrip.guidesID.contains(currentUserID))
                {
                    intent2.putExtra("trip_id", curTrip.id)
                    context.startActivity(intent2)
                    return@setOnClickListener
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return tripData.size
    }
}