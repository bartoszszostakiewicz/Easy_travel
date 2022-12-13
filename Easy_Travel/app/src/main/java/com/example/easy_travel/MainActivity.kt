package com.example.easy_travel

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
//import kotlinx.android.synthetic.main.trip_plan.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username = findViewById<TextView>(R.id.username)
        var password = findViewById<TextView>(R.id.password)
        var btn = findViewById<MaterialButton>(R.id.login_button)
        //admin and admin

        btn.setOnClickListener {
        Log.d("TAD", "Button clicked")


            Log.d("TAD", "Connection established");
            if (username.text.toString() == "admin" || password.text.toString() == "") {
                var mes1 = Toast.makeText(applicationContext, "LOGIN SUCCESFUL", Toast.LENGTH_LONG)
                mes1.show()

                var nowaAktywnosc: Intent = Intent(applicationContext, MenuActivity::class.java)
                startActivity(nowaAktywnosc)
            } else {
                var mes2 = Toast.makeText(applicationContext, "LOGIN FAILED", Toast.LENGTH_LONG)
                mes2.show()
            }
        }

    }

//    //Plan wycieczki
//
}