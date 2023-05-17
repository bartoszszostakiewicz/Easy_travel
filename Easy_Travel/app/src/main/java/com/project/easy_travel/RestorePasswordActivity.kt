package com.project.easy_travel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.ViewModel.LoginActivity
import com.project.easy_travel.ViewModel.RegisterActivity

class RestorePasswordActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var email : String

    val TAG = "pwd_recover"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)
        auth = Firebase.auth

        findViewById<MaterialButton>(R.id.next_btn).setOnClickListener {
            email = findViewById<EditText>(R.id.email).text.toString()
            if(email.isNullOrEmpty())
                return@setOnClickListener

            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                confirmReset()
            }.addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }

        }

    }

    private fun confirmReset(){
        setContentView(R.layout.activity_restore_password_2)

        var btn_resend = findViewById<MaterialButton>(R.id.prev_btn_reg)
        btn_resend.setOnClickListener {
            auth.sendPasswordResetEmail(email).addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
        }

        var btn_confirm = findViewById<MaterialButton>(R.id.finn_btn_reg)
        btn_confirm.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}