package com.project.easy_travel.Model

import com.google.firebase.database.*

data class Trip(
    var title: String?=null,
    var describe: String?=null,
    var administratorID: String? = null,
    var ID: String?=null
) {
    // Metoda sprawdza czy Firebase Realtime Database posiada tablice o nazwie <databaseTableName>. Jeżeli nie to wtedy zostaje ona stworzona.
    fun creatingTable(databaseTableName: String = "trips", create: Boolean = false) {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val tripsRef: DatabaseReference = db.getReference(databaseTableName)
        tripsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    tripsRef.setValue("");
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToDatabase(databaseTableName: String = "trips") {
        creatingTable()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val tripsRef: DatabaseReference = db.getReference(databaseTableName)
        val newTripRef = tripsRef.push()
        newTripRef.child("title").setValue(title)
        newTripRef.child("describe").setValue(describe)
        newTripRef.child("administratorID").setValue(administratorID)
        newTripRef.child("ID").setValue(newTripRef.key)
        this.ID = newTripRef.key
    }

    // Aktualizuje dane na podstawie instancji klasy
    fun updateTripByID(ID: String? = this.ID, databaseTableName: String = "trips") {
        val database = FirebaseDatabase.getInstance()
        val tripsRef = database.getReference(databaseTableName)
        val tripRef = tripsRef.child(ID.toString())
        val updates = HashMap<String, Any>()

        updates["title"] = this.title.toString()
        updates["describe"] = this.describe.toString()
        updates["administratorID"] = this.administratorID.toString()
        tripRef.updateChildren(updates)  // <-- dodaje aktualizacje do bazy danych (JEST ASYNCHRONICZNĄ!!!)
    }

    // FUNKCJA ASYNCHRONICZNA (Wyszukuje po ID użytkownika i przepisuje te wartości do tej instancji)
    fun findTripById(Id: String, databaseTableName: String = "trips") {
        val database = FirebaseDatabase.getInstance()
        val tripsRef = database.getReference(databaseTableName)
        val tripRef = tripsRef.child(Id)

        tripRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val trip = dataSnapshot.getValue(Trip::class.java)
                if (trip != null) {
                    title = trip.title
                    describe = trip.describe
                    administratorID = trip.administratorID
                    ID = Id
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}

