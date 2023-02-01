package com.project.easy_travel.ViewModel

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.User
import com.project.easy_travel.Model.Wycieczka

fun writeNewUser(userId: String, name:String,lastname:String, email:String, password:String){




}

fun writeNewTrip(name:String,przewodnik:String, opis:String, key:String){

    val database = Firebase.database.reference

    val trip = Wycieczka(name,przewodnik,opis,key)
    trip.genKey()
    database.child("trip").child(trip.key.toString()).setValue(trip)


}


