package com.project.easy_travel.ViewModel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.easy_travel.MainApplication
import com.project.easy_travel.Model.User
import com.project.easy_travel.R


class Chat_Activity_B : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var tripId:String
    private lateinit var tripViewModel: TripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_b)
        supportActionBar?.hide()


        val application = applicationContext as MainApplication
        tripViewModel = application.tripViewModel

        var participantsId: List<String> = ArrayList()
        var organiserId :String
        var guideId :List<String> = ArrayList()

        tripViewModel.data.observe(this, Observer {trip ->
            tripId = trip.id
            participantsId = emptyList()
            participantsId += trip.participantsID
            participantsId += trip.organizerID
            participantsId += trip.guidesID
        })


        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this,userList)

        userRecyclerView = findViewById(R.id.userRecycleView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter


        mDbRef.child("users").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)

                    mAuth.currentUser?.email?.let { mAuthEmail ->
                        currentUser?.email?.let { currentUserEmail ->
                            if (mAuthEmail != currentUserEmail && replaceDotsWithEmail(
                                    currentUserEmail.toString()
                                ) in participantsId
                            ) {
                                userList.add(currentUser!!)
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.d("User123","error")
            }

        })
    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }

}



