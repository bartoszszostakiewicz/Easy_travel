package com.project.easy_travel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.ViewModel.Chat_Activity_B
// firebase log
import com.google.firebase.auth.FirebaseAuth

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val application = applicationContext as MainApplication
        val tripViewModel = application.tripViewModel

        // get log user id by firebase
        val user = replaceDotsWithEmail(FirebaseAuth.getInstance().currentUser?.email.toString())


        tripViewModel.data.observe(this, Observer {trip ->
            if (trip.organizerID == user) {
                findViewById<Button>(R.id.organizer_chat_button).isEnabled = false
                findViewById<Button>(R.id.organizer_chat_button).visibility = Button.GONE
            }
        })

        var backBtn = findViewById<Button>(R.id.back_button)

        backBtn.setOnClickListener {
            this.finish()
        }

        findViewById<Button>(R.id.organizer_chat_button).setOnClickListener {
            val newActivity: Intent = Intent(applicationContext, Chat_Activity_B::class.java)
            newActivity.putExtra("OnlyOrganizer", true)
            startActivity(newActivity)
        }

    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }
}