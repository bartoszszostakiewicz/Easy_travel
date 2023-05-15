package com.project.easy_travel.Model


import android.util.Log
import com.google.firebase.database.*


data class User(
    var id: String = "",
    var name: String = "",
    val surname: String = "",
    val email: String = "",
    val tripsID: List<String> = listOf()
) : Mapable {
    override fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "surname" to surname,
            "email" to email,
            "tripsID" to tripsID
        )
    }
}