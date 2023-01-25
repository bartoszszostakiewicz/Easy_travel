package com.project.easy_travel.Model

import com.google.firebase.database.*

data class Chat(
    var tripID: String?=null,
    var commentsID: List<String?>?=null,
    var ID: String?=null
) {
    // Metoda sprawdza czy Firebase Realtime Database posiada tablice o nazwie <databaseTableName>. Jeżeli nie to wtedy zostaje ona stworzona.
    fun creatingTable(databaseTableName: String = "chats", create: Boolean = false) {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val chatsRef: DatabaseReference = db.getReference(databaseTableName)
        chatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatsRef.setValue("");
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToDatabase(databaseTableName: String = "chats") {
        creatingTable()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val chatsRef: DatabaseReference = db.getReference(databaseTableName)
        val newChatRef = chatsRef.push()
        newChatRef.child("tripID").setValue(tripID)
        newChatRef.child("commentsID").setValue(commentsID)
        newChatRef.child("ID").setValue(newChatRef.key)
        this.ID = newChatRef.key
    }

    // Aktualizuje dane na podstawie instancji klasy
    fun updateChatByID(ID: String? = this.ID, databaseTableName: String = "chats") {
        val database = FirebaseDatabase.getInstance()
        val chatsRef = database.getReference(databaseTableName)
        val chatRef = chatsRef.child(ID.toString())
        val updates = HashMap<String, Any>()

        updates["tripID"] = this.tripID.toString()
        updates["commentsID"] = this.commentsID.toString()

        chatRef.updateChildren(updates)  // <-- dodaje aktualizacje do bazy danych (JEST ASYNCHRONICZNĄ!!!)
    }

    // FUNKCJA ASYNCHRONICZNA (Wyszukuje po ID użytkownika i przepisuje te wartości do tej instancji)
    fun findChatById(Id: String, databaseTableName: String = "chats") {
        val database = FirebaseDatabase.getInstance()
        val chatsRef = database.getReference(databaseTableName)
        val chatRef = chatsRef.child(Id)

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chat = dataSnapshot.getValue(Chat::class.java)
                if (chat != null) {
                    tripID = chat.tripID.toString()
                    //commentsID = chat.commentsID.toString()
                    ID = Id
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
