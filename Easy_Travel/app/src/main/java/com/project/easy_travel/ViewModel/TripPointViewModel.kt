package com.project.easy_travel.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.easy_travel.Model.*
import com.project.easy_travel.repository.TripPointRepository
import com.project.easy_travel.repository.TripRepository
import java.security.cert.PolicyNode

class TripPointViewModel() : ViewModel() {
    private val repository = TripPointRepository.getInstance()
    private val _data = MutableLiveData<Point>()
    val data: LiveData<Point> get() = _data

    fun setData(point: Point) {
        _data.value = point
    }



    fun getAllItems(): LiveData<List<Point>> {
        return repository.getAllItems()
    }

    fun getById(id: String): LiveData<Point?> {
        return repository.getById(id)
    }

    fun save(trip: Point, id: String = "") {
        return repository.save(trip, id)
    }

    fun delete(id: String) {
        repository.delete(id)
    }
}