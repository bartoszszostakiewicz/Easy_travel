package com.project.easy_travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.User
import com.project.easy_travel.Model.Wycieczka
import com.project.easy_travel.ViewModel.writeNewTrip

import kotlinx.android.synthetic.main.activity_organizacja.*

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

    }
}



//GENEROWANY KOD WYCIECZKI
//KLASA PRZECHOWUJACA WYCIECZKE ?
//JAKIE DANE DO ZBIORKI WYCIECZKI?
//JAKIE DANE DO INFORMACJE