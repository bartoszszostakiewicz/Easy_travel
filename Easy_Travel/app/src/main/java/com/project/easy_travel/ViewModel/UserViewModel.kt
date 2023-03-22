package com.project.easy_travel.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.User

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user


    private val database = FirebaseDatabase.getInstance()
    private val userReference = database.getReference("users")

    fun load(userId: String) {
        userReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value as? String ?: ""
                val surname = snapshot.child("surname").value as? String ?: ""
                val email = snapshot.child("email").value as? String ?: ""
                val tripsID = snapshot.child("tripsID").value as? List<String> ?: listOf()
                val user = User(name, surname, email, tripsID)
                _user.value = user
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Load user failed", error.toException())
            }
        })
    }

    fun checkId (userId: String, callback: (Boolean) -> Unit) {
        userReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userExists = false
                for (userSnapshot in snapshot.children) {
                    val currentUserId = userSnapshot.key
                    if (currentUserId == userId) {
                        userExists = true
                        break
                    }
                }
                callback.invoke(userExists)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Check user existence failed", error.toException())
            }
        })
    }

    fun update(userId: String, user: User) {
        userReference.child(userId).setValue(user)
    }

    fun create(Id: String, user: User) {
        userReference.child(Id).setValue(user)
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}