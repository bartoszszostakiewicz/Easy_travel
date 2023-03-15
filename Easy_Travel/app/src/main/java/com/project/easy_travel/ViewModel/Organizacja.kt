package com.project.easy_travel.ViewModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.project.easy_travel.R

class Organizacja : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizacja)


        var name = findViewById<TextView>(R.id.nazwa_wycieczki)
        var przew = findViewById<TextView>(R.id.przewodnik)
        var ops = findViewById<TextView>(R.id.opis)

        var btn = findViewById<MaterialButton>(R.id.stworz_button)

        //val Lista_wycieczek: MutableList<Wycieczka> = mutableListOf()

        btn.setOnClickListener {
            writeNewTrip(name.text.toString(),przew.text.toString(),ops.text.toString(),"0")
            //Lista_wycieczek.add(pierwsza)
        }

        findViewById<MaterialButton>(R.id.add_pin).setOnClickListener{

            startActivity(Intent(applicationContext,Pins::class.java))

        }
    }
}



//GENEROWANY KOD WYCIECZKI
//KLASA PRZECHOWUJACA WYCIECZKE ?
//JAKIE DANE DO ZBIORKI WYCIECZKI?
//JAKIE DANE DO INFORMACJE