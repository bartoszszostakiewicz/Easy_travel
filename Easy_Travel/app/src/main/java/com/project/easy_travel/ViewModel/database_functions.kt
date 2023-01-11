package com.project.easy_travel.ViewModel

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.User

fun writeNewUser(userId: String, name:String,lastname:String, email:String, password:String){

    val database = Firebase.database.reference

    val user = User(name,lastname,email,password)

    database.child("users").child(userId).setValue(user)


}