package com.project.easy_travel.Model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Point (
    val name: String,
    val describe: String,
    val lat: Double,
    val lng: Double) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "describe" to describe,
            "lat" to lat,
            "lng" to lng
        )
    }
}

lateinit var database: DatabaseReference





