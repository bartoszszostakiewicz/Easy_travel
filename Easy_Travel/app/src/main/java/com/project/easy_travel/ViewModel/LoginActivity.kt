package com.project.easy_travel.ViewModel

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.R
import com.project.easy_travel.RestorePasswordActivity
import com.project.easy_travel.SettingsActivity
import com.project.easy_travel.TripListActivity
import com.project.easy_travel.remote.UserViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auth = Firebase.auth
        if(auth.currentUser != null)
            auth.signOut()

        var prefs = PreferenceManager.getDefaultSharedPreferences(this)
        var savedUsername = prefs.getString("remembered_login", "")


        var username = findViewById<TextView>(R.id.username)
        username.text = savedUsername
        var password = findViewById<TextView>(R.id.password)

        var btn = findViewById<MaterialButton>(R.id.login_button)
        var register = findViewById<TextView>(R.id.register)

        //userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Dodanie funkcjonalności, że jeśli użytkownik jest zalogowany, to od razu przenosi do listy wycieczek
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, TripListActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }


        btn.setOnClickListener{


            login(username.text.toString(),password.text.toString())
            /**
            userViewModel.checkId(replaceDotsWithEmail(username.text.toString())) { emailExists ->
                if (emailExists) {
                    userViewModel.load(replaceDotsWithEmail(username.text.toString()))
                    val intent = Intent(applicationContext, TripListActivity::class.java)
                    intent.putExtra("userId", replaceDotsWithEmail(username.text.toString()))
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, TripListActivity::class.java))
                }

            }
            **/


            //startActivity(Intent(applicationContext, Organizacja::class.java))

        }

        //NOTE: przechodzi do ustawien
        findViewById<ImageView>(R.id.create_trip_button).setOnClickListener{
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

        findViewById<TextView>(R.id.forgotpass).setOnClickListener {
            startActivity(Intent(applicationContext, RestorePasswordActivity::class.java))
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



}