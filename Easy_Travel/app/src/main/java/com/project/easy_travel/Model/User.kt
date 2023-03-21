package com.project.easy_travel.Model


import android.util.Log
import com.google.firebase.database.*


data class User(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val trips: List<String> = listOf()
)