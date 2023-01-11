package com.project.easy_travel.Model


data class Wycieczka(val name: String? = null, val przewodnik:String?=null, val opis: String?=null, var key: String?="0" ) {

    fun genKey() {
        key= (0..99999999).random().toString()
    }

}

