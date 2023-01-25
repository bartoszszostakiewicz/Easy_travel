package com.project.easy_travel.Model

import com.google.firebase.database.*

data class PrivateChat(
    var tripID: String?=null,
    var userID: String?=null,
    var commentsID: List<String?>?=null,
    var ID: String?=null
) {
    // Metoda sprawdza czy Firebase Realtime Database posiada tablice o nazwie <databaseTableName>. Jeżeli nie to wtedy zostaje ona stworzona.
    fun creatingTable(databaseTableName: String = "privateChats", create: Boolean = false) {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val privateChatsRef: DatabaseReference = db.getReference(databaseTableName)
        privateChatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    privateChatsRef.setValue("");
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToDatabase(databaseTableName: String = "privateChats") {
        creatingTable()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val privateChatsRef: DatabaseReference = db.getReference(databaseTableName)
        val newPrivateChatRef = privateChatsRef.push()
        newPrivateChatRef.child("tripID").setValue(tripID)
        newPrivateChatRef.child("userID").setValue(userID)
        newPrivateChatRef.child("commentsID").setValue(commentsID)
        newPrivateChatRef.child("ID").setValue(newPrivateChatRef.key)
        this.ID = newPrivateChatRef.key
    }

    // Aktualizuje dane na podstawie instancji klasy
    fun updatePrivateChatByID(ID: String? = this.ID, databaseTableName: String = "privateChats") {
        val database = FirebaseDatabase.getInstance()
        val privateChatsRef = database.getReference(databaseTableName)
        val privateChatRef = privateChatsRef.child(ID.toString())
        val updates = HashMap<String, Any>()

        updates["tripID"] = this.tripID.toString()
        updates["userID"] = this.userID.toString()
        updates["commentsID"] = this.commentsID.toString()
        privateChatRef.updateChildren(updates)  // <-- dodaje aktualizacje do bazy danych (JEST ASYNCHRONICZNĄ!!!)
    }

    // FUNKCJA ASYNCHRONICZNA (Wyszukuje po ID użytkownika i przepisuje te wartości do tej instancji)
    fun findPrivateChatById(Id: String, databaseTableName: String = "privateChats") {
        val database = FirebaseDatabase.getInstance()
        val privateChatsRef = database.getReference(databaseTableName)
        val privateChatRef = privateChatsRef.child(Id)

        privateChatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val privateChat = dataSnapshot.getValue(PrivateChat::class.java)
                if (privateChat != null) {
                    tripID = privateChat.tripID.toString()
                    userID = privateChat.userID.toString()
                    //commentsID = privateChat.commentsID.toString()
                    ID = Id
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
