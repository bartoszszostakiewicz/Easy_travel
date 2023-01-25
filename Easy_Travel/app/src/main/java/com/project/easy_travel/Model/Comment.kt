package com.project.easy_travel.Model

import com.google.firebase.database.*
import java.util.*

data class Comment(
    var userID: String?=null,
    var text: String?=null,
    var postDate: Date?=null,
    var ID: String?=null
) {
    // Metoda sprawdza czy Firebase Realtime Database posiada tablice o nazwie <databaseTableName>. Jeżeli nie to wtedy zostaje ona stworzona.
    fun creatingTable(databaseTableName: String = "comments", create: Boolean = false) {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val commentsRef: DatabaseReference = db.getReference(databaseTableName)
        commentsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    commentsRef.setValue("");
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToDatabase(databaseTableName: String = "comments") {
        creatingTable()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val commentsRef: DatabaseReference = db.getReference(databaseTableName)
        val newCommentRef = commentsRef.push()
        newCommentRef.child("userID").setValue(userID)
        newCommentRef.child("text").setValue(text)
        newCommentRef.child("postDate").setValue(postDate)
        newCommentRef.child("ID").setValue(newCommentRef.key)
        this.ID = newCommentRef.key
    }

    // Aktualizuje dane na podstawie instancji klasy
    fun updateCommentByID(ID: String? = this.ID, databaseTableName: String = "comments") {
        val database = FirebaseDatabase.getInstance()
        val commentsRef = database.getReference(databaseTableName)
        val commentRef = commentsRef.child(ID.toString())
        val updates = HashMap<String, Any>()

        updates["userID"] = this.userID.toString()
        updates["text"] = this.text.toString()
        updates["postDate"] = this.postDate.toString()
        commentRef.updateChildren(updates)  // <-- dodaje aktualizacje do bazy danych (JEST ASYNCHRONICZNĄ!!!)
    }

    // FUNKCJA ASYNCHRONICZNA (Wyszukuje po ID użytkownika i przepisuje te wartości do tej instancji)
    fun findCommentById(Id: String, databaseTableName: String = "comments") {
        val database = FirebaseDatabase.getInstance()
        val commentsRef = database.getReference(databaseTableName)
        val commentRef = commentsRef.child(Id)

        commentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val comment = dataSnapshot.getValue(Comment::class.java)
                if (comment != null) {
                    userID = comment.userID.toString()
                    text = comment.text.toString()
                    //postDate = comment.postDate.toString()
                    ID = Id
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}

