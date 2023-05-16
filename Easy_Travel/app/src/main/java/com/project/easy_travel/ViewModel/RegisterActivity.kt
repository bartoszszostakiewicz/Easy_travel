package com.project.easy_travel.ViewModel

import android.content.Intent
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
import com.project.easy_travel.remote.UserViewModel


class RegisterActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    var user_name: String = ""
    var user_surname: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        registerScreen1()
/*
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

 */

    }

    private fun registerScreen1()
    {
        setContentView(R.layout.activity_register_1)
        var field_name = findViewById<TextView>(R.id.name_edttxt)
        var field_surname = findViewById<TextView>(R.id.surname_edttxt)
        findViewById<Button>(R.id.next_btn_reg).setOnClickListener {

            user_name = field_name.text.toString()
            user_surname = field_surname.text.toString()

            registerScreen2()

        }

    }

    private fun registerScreen2()
    {
        setContentView(R.layout.activity_register_2)

        findViewById<Button>(R.id.finn_btn_reg).setOnClickListener{

            var email = findViewById<TextView>(R.id.email).text.toString()
            var password = findViewById<TextView>(R.id.password).text.toString()
            var password2 = findViewById<TextView>(R.id.password2).text.toString()

            Log.d("TAG", "onCreate: $password $password2")
            if (password == password2) {
                Log.d("TAG", "onCreate: $email $password")
                register(user_name, user_surname, email, password)
            }
        }

        findViewById<Button>(R.id.prev_btn_reg).setOnClickListener{
            registerScreen1()
        }
    }

    fun register(name: String, surname: String, email: String, password: String) {
        var auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Succesfully Registered", Toast.LENGTH_SHORT).show()

                val newUser = User(name, surname, email)
                userViewModel.save(newUser, replaceDotsWithEmail(email))

                this.finish()
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }



}