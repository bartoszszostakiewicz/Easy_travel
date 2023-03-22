package com.project.easy_travel.Model

import com.google.firebase.database.*

data class Trip(
    val title: String = "",
    val description: String = "",
    val tripPointsID: List<String> = listOf(),
    val organizerID: String = "",
    val guidesID: List<String> = listOf(),
    val participantsID: List<String> = listOf(),
)
