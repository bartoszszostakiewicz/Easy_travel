package com.example.easy_travel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var username = findViewById<TextView>(R.id.username)
        var password = findViewById<TextView>(R.id.password)
        var btn = findViewById<MaterialButton>(R.id.login_button)
        //admin and admin

        btn.setOnClickListener {
            if(username.text.toString().equals("admin") && password.text.toString().equals("admin")){
                var mes1 =Toast.makeText(applicationContext, "LOGIN SUCCESFUL", Toast.LENGTH_LONG)
                mes1.show()

                var nowaAktywnosc: Intent = Intent(applicationContext, ListaWycieczek::class.java)
                startActivity(nowaAktywnosc)
            }
            else{
                var mes2 =Toast.makeText(applicationContext, "LOGIN FAILED", Toast.LENGTH_LONG)
                mes2.show()
            }
        }

    }
}