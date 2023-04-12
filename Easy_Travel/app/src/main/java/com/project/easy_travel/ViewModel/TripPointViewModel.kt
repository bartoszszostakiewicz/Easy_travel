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
import com.project.easy_travel.Model.TripPoint
import com.project.easy_travel.Model.User
import java.util.*

class TripPointViewModel(application: Application) : AndroidViewModel(application) {
    private val _tripPoint = MutableLiveData<TripPoint>()
    val tripPoint: LiveData<TripPoint> = _tripPoint


    private val database = FirebaseDatabase.getInstance()
    private val userReference = database.getReference("tripPoints")

    fun load(tripPointId: String) {
        userReference.child(tripPointId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val title = snapshot.child("title").value as? String ?: ""
                val description = snapshot.child("description").value as? String ?: ""
                val latitude = snapshot.child("latitude").value as? Double ?: -19.9492463
                val longitude = snapshot.child("longitude").value as? Double ?: -69.6336635
                val dateStarted = snapshot.child("dateStarted").value as? Date ?: ""
                val dateFinished = snapshot.child("dateFinished").value as? Date ?: ""
                val isFinished = snapshot.child("isFinished").value as? Boolean ?: false
                val tripPoint = TripPoint(title, description, latitude, longitude, dateStarted as Date, dateFinished as Date, isFinished)
                _tripPoint.value = tripPoint
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Load trip point failed", error.toException())
            }
        })
    }

    fun update(userId: String, user: User) {
        userReference.child(userId).setValue(user)
    }

    fun create(user: User) {
        userReference.push().setValue(user)
    }

    companion object {
        private const val TAG = "TripPointViewModel"
    }
}