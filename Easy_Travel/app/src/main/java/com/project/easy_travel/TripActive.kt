package com.project.easy_travel

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.ViewModel.TripViewModel
import java.text.SimpleDateFormat
import java.util.*


class TripActive (
    private val tripData: MutableList<Trip>,
    private val intent: Intent,
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

        holder.itemView.apply {
            var detailButton = findViewById<Button>(R.id.detailButton)
            var startDateText = findViewById<TextView>(R.id.startDateTitle)
            var tripTitle = findViewById<TextView>(R.id.tripTitle)
            if (curTrip.startDate == 0L) {
                startDateText.text = "Data rozpoczęcia: Nie zaznaczono"
            } else if (curTrip.startDate < Date().time) {
                startDateText.text = "Wycieczka w trakcie"
            } else {
                startDateText.text = "Data rozpoczęcia: ${convertTimestampToDate(curTrip.startDate)}"
            }


            tripTitle.text = curTrip.title
            detailButton.setOnClickListener {
                tripViewModel.setData(curTrip)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return tripData.size
    }
}