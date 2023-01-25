package com.project.easy_travel.Model

import com.google.firebase.database.*

data class Administrator(
    var name: String?=null,
    var surname:String?=null,
    var email: String?=null,
    var password: String?=null,
    var ID: String?=null

) {

    // Metoda sprawdza czy Firebase Realtime Database posiada tablice o nazwie <databaseTableName>. Jeżeli nie to wtedy zostaje ona stworzona.
    fun creatingTable(databaseTableName: String = "administrators", create: Boolean = false) {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val administratorsRef: DatabaseReference = db.getReference(databaseTableName)
        administratorsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    administratorsRef.setValue("");
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToDatabase(databaseTableName: String = "administrators") {
        creatingTable()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val administratorsRef: DatabaseReference = db.getReference(databaseTableName)
        val newAdministratorRef = administratorsRef.push()
        newAdministratorRef.child("name").setValue(name)
        newAdministratorRef.child("surname").setValue(surname)
        newAdministratorRef.child("email").setValue(email)
        newAdministratorRef.child("password").setValue(password)
        newAdministratorRef.child("ID").setValue(newAdministratorRef.key)
        this.ID = newAdministratorRef.key
    }

    // Aktualizuje dane na podstawie instancji klasy
    fun updateAdministatorByID(ID: String? = this.ID, databaseTableName: String = "administrators") {
        val database = FirebaseDatabase.getInstance()
        val administratorsRef = database.getReference(databaseTableName)
        val administratorRef = administratorsRef.child(ID.toString())
        val updates = HashMap<String, Any>()

        updates["name"] = this.name.toString()
        updates["surname"] = this.surname.toString()
        updates["email"] = this.email.toString()
        updates["password"] = this.password.toString()
        administratorRef.updateChildren(updates)  // <-- dodaje aktualizacje do bazy danych (JEST ASYNCHRONICZNĄ!!!)
    }

    // FUNKCJA ASYNCHRONICZNA (Wyszukuje po ID użytkownika i przepisuje te wartości do tej instancji)
    fun findAdministratorById(Id: String, databaseTableName: String = "administrators") {
        val database = FirebaseDatabase.getInstance()
        val administratorsRef = database.getReference(databaseTableName)
        val administratorRef = administratorsRef.child(Id)

        administratorRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val administrator = dataSnapshot.getValue(Administrator::class.java)
                if (administrator != null) {
                    name = administrator.name.toString()
                    surname = administrator.surname.toString()
                    email = administrator.email.toString()
                    password = administrator.password.toString()
                    ID = Id
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}
