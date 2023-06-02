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

        /*************************************************************************************************************************************************************************************/
        val application = applicationContext as MainApplication
        tripViewModel = application.tripViewModel

        var participantsId: List<String> = ArrayList()
        var organiserId :String
        var guideId :List<String> = ArrayList()

        tripViewModel.data.observe(this, Observer {trip ->
            tripId = trip.id

            //Log.d("User123",tripId)
            //Log.d("User123",trip.participantsID.toString())
            //Log.d("User123",trip.organizerID.toString())
            //Log.d("User123",trip.guidesID.toString())
            participantsId = emptyList()
            participantsId += trip.participantsID
            participantsId += trip.organizerID
            participantsId += trip.guidesID

            Log.d("test123",participantsId.toString())
        })



        /*************************************************************************************************************************************************************************************/

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this,userList)

        userRecyclerView = findViewById(R.id.userRecycleView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter


        Log.d("User123","prev")
        mDbRef.child("users").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    //Log.d("User123",currentUser.toString())
//zmienic logike !!!!!!!
                    if(mAuth.currentUser?.email != currentUser?.email ){
                       // if(currentUser?.tripsID?.contains(tripId) == true)
                        //Log.d("tripid",tripId)
                        //Log.d("User123",currentUser.toString())
                        Log.d("User123",participantsId.toString())
                        if(currentUser?.email?.contains(participantsId.toString()) == true) {
                            userList.add(currentUser!!)
                                //Log.d("User123",currentUser.toString())
                            //Log.d("User123",participantsId.toString())
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
        return email.replace("_", ".")
    }

}



