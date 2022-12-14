package com.example.easy_travel

import android.widget.TextView

class Wycieczka(
    val name: TextView,
    val przewodnik: TextView,
    )
{
    fun get_key(): Int {
        val rand = (0..10).random()
        return rand
    }

}