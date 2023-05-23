package com.project.easy_travel.Model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference

data class Point(
    var id: String = "",
    val name: String = "",
    val describe: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    val startDate: Long = 0L,
    val finishDate: Long = 0L
) : Mapable {

    constructor() : this("", "", "", 0.0, 0.0, 0L, 0L)

    override fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "describe" to describe,
            "lat" to lat,
            "lng" to lng,
            "startDate" to startDate,
            "finishDate" to finishDate
        )
    }

    fun toLatLng(): LatLng {
        return LatLng(lat, lng)
    }
}


lateinit var database: DatabaseReference






