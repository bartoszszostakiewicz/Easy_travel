package com.project.easy_travel.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.User
//import com.project.easy_travel.remote.UserSource

class UserRepository {

    private val NAME_REF = "users"

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val localRef: DatabaseReference = rootRef.child(NAME_REF)

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: UserRepository().also { instance = it }
            }
    }

    fun getAllItems(): LiveData<List<User>> {
        val dataList = MutableLiveData<List<User>>()

        localRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<User>()
                for (data in snapshot.children) {
                    val id = data.key
                    val item = data.getValue(User::class.java)?.apply {
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

    fun getById(id: String): MutableLiveData<User?> {
        val data = MutableLiveData<User?>()

        localRef.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.getValue(User::class.java)
                data.value = item
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseRepository", "Failed to read data item with ID $id", error.toException())
            }
        })

        return data
    }

    fun save(data: User, id: String = "") {
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
