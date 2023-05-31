package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.TripCell
import com.project.easy_travel.ViewModel.TripPointViewModel
import kotlinx.coroutines.*
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.*

class TripListPointActivity : AppCompatActivity() {
    private lateinit var tripAdapter: TripPointAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_plan)

        val application = applicationContext as MainApplication

        val tripViewModel = application.tripViewModel
        val tripPointViewModel = application.tripPointViewModel

        val tripItems: MutableList<Point> = mutableListOf()

        var pointsId: List<String> = listOf()
        var tripID: String = ""

        tripAdapter = TripPointAdapter(tripItems, R.layout.trip_plan_element, tripPointViewModel)

        tripViewModel.data.observe(this, Observer {trip ->
            pointsId = trip.tripPointsID
            tripID = trip.id

            for (i in pointsId.indices) {
                tripPointViewModel.getById(pointsId[i]).observe(this, Observer { point ->
                    if (point != null) {

                        tripItems.add(point)
                        // Update trip adapter
                        tripAdapter.notifyDataSetChanged()
                    }
                })

            }
        })

        findViewById<Button>(R.id.buttonBack).setOnClickListener {
            this.finish()
        }
    }
}

class TripPointAdapter (
    private val tripData: MutableList<Point>,
    private val xmlFile: Int,
    private val tripPointViewModel: TripPointViewModel
) : RecyclerView.Adapter<TripPointAdapter.TripViewHolder>()//AppCompatActivity()
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
        val curTripPoint = tripData[position]
        Log.d("tripPoint", curTripPoint.toString())

        holder.itemView.apply {
            var tripText = findViewById<TextView>(R.id.point_trip_element_title_txt)
            var describeText = findViewById<TextView>(R.id.point_trip_element_describe_txt)
            var change_btn = findViewById<Button>(R.id.change_btn)

            tripText.text = curTripPoint.name
            describeText.text = curTripPoint.describe

            change_btn.setOnClickListener {
                ;
            }
        }
    }

    override fun getItemCount(): Int {
        return tripData.size
    }
}