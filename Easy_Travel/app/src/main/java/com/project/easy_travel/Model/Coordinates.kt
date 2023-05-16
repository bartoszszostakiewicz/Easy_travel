package com.project.easy_travel.Model

data class Coordinates(
    val lat: Double,
    val lng: Double
)

{
    //default constructor
    constructor() : this(0.0, 0.0)
}
