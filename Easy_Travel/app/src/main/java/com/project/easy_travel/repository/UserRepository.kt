package com.project.easy_travel.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.User
//import com.project.easy_travel.remote.UserSource

class UserRepository {

    val USERS_REF = "users"

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val productRef: DatabaseReference = rootRef.child(USERS_REF)

    fun getLiveData(id: String) : MutableLiveData<User?>
    {
        val mutableLiveData = MutableLiveData<User?>();
        productRef.child(id).get().addOnCompleteListener { task ->
            var user : User? = null
            if(task.isSuccessful){
                val result = task.result
                result?.let {
                    user = it.getValue(User::class.java)
                }
            }
            mutableLiveData.value = user;
        }
        return mutableLiveData
    }

}
