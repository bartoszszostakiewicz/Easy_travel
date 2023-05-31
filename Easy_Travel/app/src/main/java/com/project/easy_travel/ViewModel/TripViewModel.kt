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
import com.project.easy_travel.repository.TripPointRepository
import com.project.easy_travel.repository.TripRepository

class TripViewModel() : ViewModel() {
    private val repository = TripRepository.getInstance()
    private val _data = MutableLiveData<Trip>()
    val data: LiveData<Trip> get() = _data

    fun setData(trip: Trip) {
        _data.value = trip
    }

    fun getAllItems(): LiveData<List<Trip>> {
        return repository.getAllItems()
    }

    fun getById(id: String): LiveData<Trip?> {
        return repository.getById(id)
    }

    fun save(trip: Trip, id: String = ""): String {
        return repository.save(trip, id)
    }

    fun delete(id: String) {
        repository.delete(id)
    }
}