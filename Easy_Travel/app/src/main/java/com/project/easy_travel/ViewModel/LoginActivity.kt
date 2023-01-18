package com.project.easy_travel.ViewModel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.project.easy_travel.Organizacja
import com.project.easy_travel.R
import com.project.easy_travel.TripListActivity


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





        btn.setOnClickListener{
            login(username.text.toString(),password.text.toString());
        }

    }

    private fun login(email:String,password:String) {
        var auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {
            if(it.isSuccessful){

                Toast.makeText(this,"Succesfully LoggedIn", Toast.LENGTH_SHORT).show()

                if(email == "organizator123456@gmail.com"){
                    startActivity(Intent(applicationContext, Organizacja::class.java))
                }else{
                    startActivity(Intent(applicationContext, TripListActivity::class.java))
                }

            }else{
                Toast.makeText(this,"Log In failed ", Toast.LENGTH_SHORT).show()
            }
        }

    }

}