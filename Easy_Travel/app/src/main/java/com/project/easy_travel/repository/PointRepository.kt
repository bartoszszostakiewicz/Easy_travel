package com.project.easy_travel.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.Trip

class PointRepository {
    val Points_REF = "trips"

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val productRef: DatabaseReference = rootRef.child(Points_REF)

    fun getLiveData(id: String) : MutableLiveData<Point?>
    {
        val mutableLiveData = MutableLiveData<Point?>();
        productRef.child(id).get().addOnCompleteListener { task ->
            var point : Point? = null
            if(task.isSuccessful){
                val result = task.result
                result?.let {
                    point = it.getValue(Point::class.java)
                }
            }
            mutableLiveData.value = point;
        }
        return mutableLiveData
    }

    fun getMultipleLiveData(ids: List<String>) : List<MutableLiveData<Point?>>
    {
        val points : MutableList<MutableLiveData<Point?>> = mutableListOf()
        for ((i, id) in ids.withIndex())
        {
            points.add(i, getLiveData(id))
        }
        return points
    }

    fun setData(id: String, data: Point)
    {
        productRef.child(id).setValue(data.toMap())
    }
}