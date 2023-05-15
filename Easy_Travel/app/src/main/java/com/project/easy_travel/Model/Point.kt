package com.project.easy_travel.Model

import com.google.firebase.database.DatabaseReference

data class Point (
    var id: String = "",
    val name: String,
    val describe: String,
    val lat: Double,
    val lng: Double
    ) : Mapable {
    override fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "describe" to describe,
            "lat" to lat,
            "lng" to lng
        )
    }
}

lateinit var database: DatabaseReference





