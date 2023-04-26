package com.project.easy_travel.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.easy_travel.Model.*
import com.project.easy_travel.repository.MainRepository
import com.project.easy_travel.repository.TripRepository

class TripViewModel(
    private val tripRepository: TripRepository = MainRepository.getTripRepository()
) : ViewModel() {

    fun getResponse(userId: String) : LiveData<Trip?>{
        return  tripRepository.getLiveData(userId)
    }

/*
    private val _trip = MutableLiveData<Trip>()
    val trip: LiveData<Trip> = _trip

    private val database = FirebaseDatabase.getInstance()
    private val userReference = database.getReference("trips")

    fun load(tripId: String) {
        userReference.child(tripId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val title = snapshot.child("title").value as? String ?: ""
                val description = snapshot.child("description").value as? String ?: ""
                val tripPointsID = snapshot.child("tripPointsID").value as? List<String> ?: listOf()
                val organizerID = snapshot.child("organizerID").value as? String ?: ""
                val guidesID = snapshot.child("guidesID").value as? List<String> ?: listOf()
                val participantsID = snapshot.child("participantsID").value as? List<String> ?: listOf()

                val trip = Trip(title, description, tripPointsID, organizerID, guidesID, participantsID)

                _trip.value = trip
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Load trip failed", error.toException())
            }
        })
    }

    fun update(tripId: String, trip: Trip) {
        userReference.child(tripId).setValue(trip)
    }

    // Create a new user without ID (default ID is generated)
    fun create(trip: Trip) {
        userReference.push().setValue(trip)
    }

    companion object {
        private const val TAG = "TripViewModel"
    }
    */

}