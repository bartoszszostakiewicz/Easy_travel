package com.project.easy_travel.Model

import com.google.firebase.database.*
import java.util.Date

data class TripPoint(
    var tripID: String?=null,
    var title: String?=null,
    var describe: String?=null,
    var dateStart: Date?=null,
    var dateFinish: Date?=null,
    var isDone: Boolean?=null,
    var ID: String?=null
) {
    // Metoda sprawdza czy Firebase Realtime Database posiada tablice o nazwie <databaseTableName>. Jeżeli nie to wtedy zostaje ona stworzona.
    fun creatingTable(databaseTableName: String = "tripPoints", create: Boolean = false) {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val TripPointsRef: DatabaseReference = db.getReference(databaseTableName)
        TripPointsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    TripPointsRef.setValue("");
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToDatabase(databaseTableName: String = "tripPoints") {
        creatingTable()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val tripPointsRef: DatabaseReference = db.getReference(databaseTableName)
        val newTripPointRef = tripPointsRef.push()
        newTripPointRef.child("tripID").setValue(tripID)
        newTripPointRef.child("title").setValue(title)
        newTripPointRef.child("describe").setValue(describe)
        newTripPointRef.child("dateStart").setValue(dateStart)
        newTripPointRef.child("dateFinish").setValue(dateFinish)
        newTripPointRef.child("isDone").setValue(isDone)
        newTripPointRef.child("ID").setValue(newTripPointRef.key)
        this.ID = newTripPointRef.key
    }

    // Aktualizuje dane na podstawie instancji klasy
    fun updateTripPointByID(ID: String? = this.ID, databaseTableName: String = "tripPoints") {
        val database = FirebaseDatabase.getInstance()
        val tripPointsRef = database.getReference(databaseTableName)
        val tripPointRef = tripPointsRef.child(ID.toString())
        val updates = HashMap<String, Any>()

        updates["tripID"] = this.tripID.toString()
        updates["title"] = this.title.toString()
        updates["describe"] = this.describe.toString()
        updates["dateStart"] = this.dateStart.toString()
        updates["dateFinish"] = this.dateFinish.toString()
        updates["isDone"] = this.isDone.toString()
        tripPointRef.updateChildren(updates)  // <-- dodaje aktualizacje do bazy danych (JEST ASYNCHRONICZNĄ!!!)
    }

    // FUNKCJA ASYNCHRONICZNA (Wyszukuje po ID użytkownika i przepisuje te wartości do tej instancji)
    fun findTripPointById(Id: String, databaseTableName: String = "tripPoints") {
        val database = FirebaseDatabase.getInstance()
        val tripPointsRef = database.getReference(databaseTableName)
        val tripPointRef = tripPointsRef.child(Id)

        tripPointRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tripPoint = dataSnapshot.getValue(TripPoint::class.java)
                if (tripPoint != null) {
                    tripID = tripPoint.tripID
                    title = tripPoint.title
                    describe = tripPoint.describe
                    dateStart = tripPoint.dateStart
                    dateFinish = tripPoint.dateFinish
                    isDone = tripPoint.isDone
                    ID = Id
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
