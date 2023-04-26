package com.project.easy_travel.Model

data class Responce<T> (
    var items: T? = null,
    var exception: Exception? = null,
)