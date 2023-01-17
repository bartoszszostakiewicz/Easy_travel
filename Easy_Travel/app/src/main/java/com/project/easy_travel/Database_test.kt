package com.project.easy_travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.User
import com.project.easy_travel.ViewModel.SHA256
import com.project.easy_travel.ViewModel.writeNewUser


class Database_test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_test)

        Log.d("TAD", "test przed zapisem")

        val db:FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = db.getReference("users").push()




        val sha = SHA256()

        val haslo = sha.SHA256("haslo")

        writeNewUser("4","Aleksander","Sochacki","alek64377@gmail.com",haslo)

        Log.d("TAD",haslo)

        //myRef.setValue(user)


        /**
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(User::class.java)

                var name = findViewById<TextView>(R.id.textView4)

                name.setText(value?.getName())

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        **/





    }
}