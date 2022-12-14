package com.example.easy_travel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

//import kotlinx.android.synthetic.main.activity_organizacja.*

class Organizacja : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizacja)

        var name = findViewById<TextView>(R.id.nazwa_wycieczki)
        var przew = findViewById<TextView>(R.id.przewodnik)
        var btn = findViewById<MaterialButton>(R.id.stworz_button)

        btn.setOnClickListener {

            var pierwsza = Wycieczka(name, przew)
            var n = pierwsza.nazwa
            var p = pierwsza.przewonik

            //Nowa_wycieczka.setText("g")
            var mes1 = Toast.makeText(applicationContext, "${n} ZOSTAŁA DODANA (${p})", Toast.LENGTH_LONG)
            mes1.show()
        }

    }
}

class Wycieczka(val nazwa: TextView, val przewonik: TextView) {
    fun id(){}

}

//GENEROWANY KOD WYCIECZKI
//KLASA PRZECHOWUJACA WYCIECZKE ?
//JAKIE DANE DO ZBIORKI WYCIECZKI?
//JAKIE DANE DO INFORMACJE