package com.project.easy_travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.User
import com.project.easy_travel.R

class AdditionalInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_information)

        val name = findViewById<EditText>(R.id.name_input)
        val surname = findViewById<EditText>(R.id.surname_input)



        val email = Firebase.auth.currentUser?.email

        val textView = findViewById<TextView>(R.id.textView6)




        val btn = findViewById<TextView>(R.id.save)

        btn.setOnClickListener {


            val user = User(name.text.toString(), surname.text.toString(), email)
            textView.text = user.name + " " + user.surname
            user.addToDatabase()
        }



    }




}