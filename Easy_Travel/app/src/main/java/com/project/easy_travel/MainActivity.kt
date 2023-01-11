package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easy_travel.R
import com.google.android.material.button.MaterialButton
import com.project.easy_travel.ViewModel.RegisterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username = findViewById<TextView>(R.id.username)
        var password = findViewById<TextView>(R.id.password)
        var btn = findViewById<MaterialButton>(R.id.login_button)

        var forgot = findViewById<TextView>(R.id.register)

        forgot.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        btn.setOnClickListener {
        Log.d("TAD", "Button clicked")


            Log.d("TAD", "Connection established");
            if (username.text.toString() == "admin" && password.text.toString() == "") {
                var mes1 = Toast.makeText(applicationContext, "LOGIN SUCCESFUL", Toast.LENGTH_LONG)
                mes1.show()

                var nowaAktywnosc: Intent = Intent(applicationContext, TripListActivity::class.java)
                startActivity(nowaAktywnosc)
            }

            if (username.text.toString() == "org" && password.text.toString() == "") {
                var mes1 = Toast.makeText(applicationContext, "LOGIN SUCCESFUL", Toast.LENGTH_LONG)
                mes1.show()

                var nowaAktywnosc2: Intent = Intent(applicationContext, Organizacja::class.java)
                startActivity(nowaAktywnosc2)
            }

            else {
                var mes2 = Toast.makeText(applicationContext, "LOGIN FAILED", Toast.LENGTH_LONG)
                mes2.show()
            }
        }

    }

//    //Plan wycieczki
//
}