package com.project.easy_travel.ViewModel

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.AdditionalInformationActivity
import com.project.easy_travel.Model.User
import com.project.easy_travel.R


class RegisterActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        findViewById<Button>(R.id.registerButton).setOnClickListener {

            var name = findViewById<TextView>(R.id.name_edttxt).text.toString()
            var surname = findViewById<TextView>(R.id.surname_edttxt).text.toString()
            var email = findViewById<TextView>(R.id.email).text.toString()
            var password = findViewById<TextView>(R.id.password).text.toString()
            var password2 = findViewById<TextView>(R.id.password2).text.toString()

            Log.d("TAG", "onCreate: $password $password2")
            if (password == password2) {
                Log.d("TAG", "onCreate: $email $password")
                register(name, surname, email, password)
            }
        }
    }

    fun register(name: String, surname: String, email: String, password: String) {
        var auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                // ListOf give example of trips
                userViewModel.create(replaceDotsWithEmail(email), User(name, surname, email, listOf("1", "2")))
                Toast.makeText(this, "Succesfully Registered", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, AdditionalInformationActivity::class.java))
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }



}