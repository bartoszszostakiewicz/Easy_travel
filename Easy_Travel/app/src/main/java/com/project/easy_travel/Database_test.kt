package com.project.easy_travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.project.easy_travel.Model.User


class Database_test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_test)

        val db:FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = db.getReference("users").push()

        var user = User()

        user.setName("Michał")
        user.setLastname("Nowak")
        //user.setPesel("01230306072")

        myRef.setValue(user)


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