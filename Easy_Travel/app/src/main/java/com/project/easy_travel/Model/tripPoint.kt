package com.project.easy_travel.Model

import com.google.firebase.database.*
import java.util.Date

data class TripPoint(
    val title: String = "",
    val description: String = "",
    val latitude: Double = -19.9492463,
    val longitude: Double = -69.6336635,
    val dateStarted: Date = Date(),
    val dateFinished: Date = Date(),
    val isFinished: Boolean = false,
)
