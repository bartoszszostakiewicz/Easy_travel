package com.project.easy_travel.Model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Point (
    val lat: Double,
    val lng: Double){
    constructor()  : this(0.0,0.0)

}



lateinit var database: DatabaseReference





