package com.project.easy_travel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CreateTrip : AppCompatActivity() {
    lateinit var nextButton1: Button
    lateinit var nextButton2: Button
    lateinit var nextButton3: Button

    lateinit var addTripPointBtn: Button

    lateinit var nameTripEdttxt: EditText
    lateinit var describeTripEdttxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add a new xml file
        setContentView(R.layout.create_trip_page1)

        // inicializacja z pierwszego xmla
        nextButton1 = findViewById<Button>(R.id.next_btn1)
        nameTripEdttxt = findViewById<EditText>(R.id.nameTrip_edttxt)
        describeTripEdttxt = findViewById<EditText>(R.id.describeTrip_edttxt)



        nextButton1.setOnClickListener {
            setContentView(R.layout.create_trip_page2)
            overridePendingTransition(R.anim.slide_right_to_left, R.anim.no_animation)

            nextButton2 = findViewById<Button>(R.id.next_btn2)
            addTripPointBtn = findViewById<Button>(R.id.addTripPoint_btn)

            nextButton2.setOnClickListener {
                setContentView(R.layout.create_trip_page3)
                nextButton3 = findViewById<Button>(R.id.create_trip_btn)
                nextButton3.setOnClickListener {
                    val intent = Intent(this, OrganizerMainActivity::class.java)
                    startActivity(intent)
                }
            }

            addTripPointBtn.setOnClickListener {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_create_trip_point)
                dialog.show()
            }
        }



    }
}