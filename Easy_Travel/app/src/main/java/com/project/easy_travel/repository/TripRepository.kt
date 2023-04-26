package com.project.easy_travel.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.User

class TripRepository {

    val TRIPS_REF = "trips"

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val productRef: DatabaseReference = rootRef.child(TRIPS_REF)

    fun getLiveData(id: String) : MutableLiveData<Trip?>
    {
        val mutableLiveData = MutableLiveData<Trip?>();
        productRef.child(id).get().addOnCompleteListener { task ->
            var trip : Trip? = null
            if(task.isSuccessful){
                val result = task.result
                result?.let {
                    trip = it.getValue(Trip::class.java)
                }
            }
            mutableLiveData.value = trip;
        }
        return mutableLiveData
    }

    fun setData(id: String, data: Trip)
    {
        productRef.child(id).setValue(data.toMap())
    }
}