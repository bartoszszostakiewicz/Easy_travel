package com.example.easy_travel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
//import kotlinx.android.synthetic.main.activity_organizacja.*

//import kotlinx.android.synthetic.main.activity_organizacja.*

class Organizacja : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizacja)

        var name = findViewById<TextView>(R.id.nazwa_wycieczki)
        var przew = findViewById<TextView>(R.id.przewodnik)
        var btn = findViewById<MaterialButton>(R.id.stworz_button)

        val Lista_wycieczek: MutableList<Wycieczka> = mutableListOf()

        btn.setOnClickListener {

            var pierwsza = Wycieczka(name, przew)
            var n = pierwsza.name
            var p = pierwsza.przewodnik

            Lista_wycieczek.add(pierwsza)
            var key =pierwsza.get_key()

            //Nowa_wycieczka.text= key.toString()
        }

    }
}



//GENEROWANY KOD WYCIECZKI
//KLASA PRZECHOWUJACA WYCIECZKE ?
//JAKIE DANE DO ZBIORKI WYCIECZKI?
//JAKIE DANE DO INFORMACJE