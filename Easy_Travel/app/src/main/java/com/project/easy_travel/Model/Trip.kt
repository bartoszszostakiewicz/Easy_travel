package com.project.easy_travel.Model

import com.google.firebase.database.*

data class Trip(
    var title: String = "",
    var description: String = "",
    val tripPointsID: List<String> = listOf(),
    val organizerID: String = "",
    val guidesID: List<String> = listOf(),
    val participantsID: List<String> = listOf(),
) : Mapable {
    override fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "description" to description,
            "tripPointsID" to tripPointsID,
            "organizerID" to organizerID,
            "guidesID" to guidesID,
            "participantsID" to participantsID
        )
    }
}
