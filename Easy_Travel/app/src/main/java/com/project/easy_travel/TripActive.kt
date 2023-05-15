package com.project.easy_travel

import android.content.Context
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

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val curTrip = tripData[position]

        holder.itemView.apply {
            var detailButton = findViewById<Button>(R.id.detailButton)
            var tripTitle = findViewById<TextView>(R.id.tripTitle)
            tripTitle.text = curTrip.title
            detailButton.setOnClickListener {
                context.startActivity(intent)
                tripViewModel.setData(curTrip)
            }
        }
    }

    override fun getItemCount(): Int {
        return tripData.size
    }
}