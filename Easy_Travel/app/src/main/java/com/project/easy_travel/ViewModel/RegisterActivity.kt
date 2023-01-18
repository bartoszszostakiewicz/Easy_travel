package com.project.easy_travel.ViewModel

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.R


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        findViewById<Button>(R.id.registerButton).setOnClickListener {

            var email = findViewById<TextView>(R.id.email).text.toString()
            var password = findViewById<TextView>(R.id.password).text.toString()
            var password2 = findViewById<TextView>(R.id.password2).text.toString()

            Log.d("TAG", "onCreate: $password $password2")
            if (password == password2) {
                Log.d("TAG", "onCreate: $email $password")
                register(email, password)
            }
        }
    }

    fun register(email: String, password: String) {
        var auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Succesfully Registered", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}