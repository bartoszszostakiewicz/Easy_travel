package com.project.easy_travel.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.project.easy_travel.Model.User
import com.project.easy_travel.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        findViewById<Button>(R.id.registerButton).setOnClickListener{
            val db: FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef: DatabaseReference = db.getReference("users").push()

            var user = User()

            user.setName(findViewById<TextView>(R.id.firstName).text.toString())
            user.setLastname(findViewById<TextView>(R.id.lastName).text.toString())
            user.setEmail(findViewById<TextView>(R.id.email).text.toString())
            //while(findViewById<TextView>(R.id.password).toString()!==findViewById<TextView>(R.id.password2).toString()){
            //  Toast.makeText(applicationContext, "Hasła są różne!", Toast.LENGTH_LONG).show()
            //}
            Log.d("TAD", findViewById<TextView>(R.id.password).text.toString())
            user.setPassword(findViewById<TextView>(R.id.password).text.toString())
            //user.setPhoneNumber(findViewById<TextView>(R.id.phone_number).toString().toInt())
            myRef.setValue(user)
        }

        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = db.getReference("users").push()

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<String>()
                Log.d(TAG, "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}