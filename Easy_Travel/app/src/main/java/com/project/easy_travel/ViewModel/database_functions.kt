package com.project.easy_travel.ViewModel

import android.util.Log

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.MainApplication
import com.project.easy_travel.Model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


fun writeNewUser(userId: String, name:String,lastname:String, email:String, password:String){




}

fun writeNewTrip(name:String,przewodnik:String, opis:String, key:String){

    val database = Firebase.database.reference

    val trip = Wycieczka(name,przewodnik,opis,key)
    trip.genKey()
    database.child("trip").child(trip.key.toString()).setValue(trip)


}

fun writeNewPoint(lat: Double, lng: Double) {
    val point = Coordinates(lat, lng)

    val database = Firebase.database.reference
    database.child("points").push().setValue(point)

}

