package com.project.easy_travel.Model

import com.google.firebase.database.*
import java.util.*

data class Trip(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val tripPointsID: List<String> = listOf(),
    val organizerID: String = "",
    val guidesID: List<String> = listOf(),
    val participantsID: List<String> = listOf(),
    val startDate: Long = 0L,
) : Mapable {
    override fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "description" to description,
            "tripPointsID" to tripPointsID,
            "organizerID" to organizerID,
            "guidesID" to guidesID,
            "participantsID" to participantsID,
            "startDate" to startDate
        )
    }
}
