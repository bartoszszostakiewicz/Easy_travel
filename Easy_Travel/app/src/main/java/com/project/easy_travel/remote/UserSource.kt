package com.project.easy_travel.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.project.easy_travel.Model.User
import com.project.easy_travel.repository.UserRepository

class UserSource : UserRepository {
    private val database = FirebaseDatabase.getInstance()
    private val ref = database.getReference("users")

    override fun get(userId: String): LiveData<User> {
        val liveData = MutableLiveData<User>()
        ref.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                user?.let { liveData.value = it }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return liveData
    }

    override fun save(user: User) {
        ref.push().setValue(user)
    }

    override fun delete(userId: String) {
        ref.child(userId).removeValue()
    }
}
