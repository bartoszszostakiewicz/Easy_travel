package com.project.easy_travel.ViewModel

import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.project.easy_travel.MenuActivity
import com.project.easy_travel.Organizacja


//import kotlinx.android.synthetic.main.trip_plan.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username = findViewById<TextView>(R.id.username)
        var password = findViewById<TextView>(R.id.password)

        var btn = findViewById<MaterialButton>(R.id.login_button)
        var register = findViewById<TextView>(R.id.register)

        register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        btn.setOnClickListener {
        Log.d("TAD", "Button clicked")


            Log.d("TAD", "Connection established");
            if (username.text.toString() == "admin" && password.text.toString() == "") {
                var mes1 = Toast.makeText(applicationContext, "LOGIN SUCCESFUL", Toast.LENGTH_LONG)
                mes1.show()

                var nowaAktywnosc: Intent = Intent(applicationContext, MenuActivity::class.java)
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