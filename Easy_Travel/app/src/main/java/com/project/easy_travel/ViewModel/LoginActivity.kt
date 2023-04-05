package com.project.easy_travel.ViewModel

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.R
import com.project.easy_travel.SettingsActivity
import com.project.easy_travel.TripListActivity

class LoginActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var prefs = PreferenceManager.getDefaultSharedPreferences(this)
        var savedUsername = prefs.getString("remembered_login", "")


        var username = findViewById<TextView>(R.id.username)
        username.text = savedUsername
        var password = findViewById<TextView>(R.id.password)

        var btn = findViewById<MaterialButton>(R.id.login_button)
        var register = findViewById<TextView>(R.id.register)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }


        btn.setOnClickListener{
            //startActivity(Intent(applicationContext, Pins::class.java))

            userViewModel.checkId(replaceDotsWithEmail(username.text.toString())) { emailExists ->
                if (emailExists) {
                    userViewModel.load(replaceDotsWithEmail(username.text.toString()))
                    val intent = Intent(applicationContext, TripListActivity::class.java)
                    intent.putExtra("userId", replaceDotsWithEmail(username.text.toString()))
                    startActivity(intent)
                }

            }



            //startActivity(Intent(applicationContext, Organizacja::class.java))

        }

        //NOTE: przechodzi do ustawien
        findViewById<MaterialButton>(R.id.create_trip_button).setOnClickListener{
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

    }

    private fun login(email:String,password:String) {
        var auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {
            if(it.isSuccessful){

                Toast.makeText(this,"Succesfully LoggedIn", Toast.LENGTH_SHORT).show()


                startActivity(Intent(applicationContext, TripListActivity::class.java))


            }else{
                Toast.makeText(this,"Log In failed ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }

}