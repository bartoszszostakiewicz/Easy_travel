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
        setContentView(R.layout.activity_register_2)

        findViewById<TextView>(R.id.textView3).text = "Zmień hasło konta Easytravel"

        var code_edit = findViewById<EditText>(R.id.email)
        code_edit.hint = "Podaj kod"

        var btn_resend = findViewById<MaterialButton>(R.id.prev_btn_reg)
        btn_resend.text = "Wyślij ponownie kod"
        btn_resend.setOnClickListener {
            auth.sendPasswordResetEmail(email).addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
        }

        var btn_confirm = findViewById<MaterialButton>(R.id.finn_btn_reg)
        btn_confirm.text = "Zmień hasło"
        btn_confirm.setOnClickListener {
            var code = code_edit.text.toString()

            var pwd1 = findViewById<EditText>(R.id.password).text.toString()
            var pwd2 = findViewById<EditText>(R.id.password2).text.toString()

            if(pwd1 != pwd2)
                return@setOnClickListener

            auth.confirmPasswordReset(code, pwd1).addOnSuccessListener {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }.addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
        }
    }
}