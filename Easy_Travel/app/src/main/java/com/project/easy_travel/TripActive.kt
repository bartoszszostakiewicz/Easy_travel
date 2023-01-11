package com.project.easy_travel

import com.example.easy_travel.R
import android.content.Context
import android.content.Intent
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.Model.TripCell


class TripActive (
    private val tripCells: MutableList<TripCell>,
    private var context: Context,
    private val intent: Intent,
    private val xmlFile: Int
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

    private fun toggleStrikeThrough(tripTitle: TextView, isOver: Boolean) {
        if (isOver) {
            tripTitle.paintFlags = STRIKE_THRU_TEXT_FLAG
        }
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val curTrip = tripCells[position]
        holder.itemView.apply {
            var detailButton = findViewById<Button>(R.id.detailButton)
            var tripTitle = findViewById<TextView>(R.id.tripTitle)
            tripTitle.text = curTrip.name
            toggleStrikeThrough(tripTitle, curTrip.isOver)
            detailButton.setOnClickListener {
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return tripCells.size
    }
}