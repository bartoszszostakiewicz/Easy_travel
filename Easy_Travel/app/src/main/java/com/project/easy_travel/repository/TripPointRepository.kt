package com.project.easy_travel.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.User

class TripPointRepository {

        private val NAME_REF = "points"

        private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        private val localRef: DatabaseReference = rootRef.child(NAME_REF)

        companion object {
            @Volatile
            private var instance: TripPointRepository? = null

            fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: TripPointRepository().also { instance = it }
                }
        }

        fun getAllItems(): LiveData<List<Point>> {
            val dataList = MutableLiveData<List<Point>>()

            localRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Point>()
                    for (data in snapshot.children) {
                        val id = data.key
                        val item = data.getValue(Point::class.java)?.apply {
                            this.id = id.toString()
                        }
                        item?.let { list.add(it) }
                    }
                    dataList.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseRepository", "Failed to read data", error.toException())
                }
            })

            return dataList
        }

        fun getById(id: String): MutableLiveData<Point?> {
            val data = MutableLiveData<Point?>()

            localRef.child(id).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val item = snapshot.getValue(Point::class.java)
                    data.value = item
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseRepository", "Failed to read data item with ID $id", error.toException())
                }
            })

            return data
        }

        fun save(data: Point, id: String = "") {
            val id = if (id.isEmpty()) localRef.push().key else id
            val values = data.toMap()

            if (id != null) {
                val childUpdates = hashMapOf<String, Any>()
                childUpdates["/$id"] = values

                localRef.updateChildren(childUpdates)
            }
            data.id = id.toString()
        }

        fun delete(id: String) {
            localRef.child(id).removeValue()
        }

}