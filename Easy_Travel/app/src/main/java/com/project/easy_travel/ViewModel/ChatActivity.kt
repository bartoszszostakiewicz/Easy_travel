package com.project.easy_travel.ViewModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.easy_travel.MessageAdapter
import com.project.easy_travel.Model.Message
import com.project.easy_travel.R

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String ?= null
    var senderRoom: String ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat2)

        var extras : Bundle? = intent.extras
        val name = extras?.getString("name")
        var receiverEmail = extras?.getString("email")

        var senderEmail = FirebaseAuth.getInstance().currentUser?.email
        //var senderEmail = FirebaseAuth.getInstance().currentUser?.email

        mDbRef = FirebaseDatabase.getInstance().getReference()


        receiverEmail = receiverEmail?.let { replaceDotsWithEmail(it) }
        senderEmail = senderEmail?.let { replaceDotsWithEmail(it) }


        senderRoom = receiverEmail + senderEmail

        receiverRoom = senderEmail + receiverEmail

        Log.d("name",name.toString())

        supportActionBar?.title = name

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sent_button)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)

        chatRecyclerView.adapter = messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {


                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }

                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendButton.setOnClickListener{

            val message = messageBox.text.toString()
            val messageObject = Message(message,senderEmail)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

            messageBox.setText("")
        }


    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }
}