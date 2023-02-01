package com.project.easy_travel.Model


import android.util.Log
import com.google.firebase.database.*


data class User(
    var name: String?=null,
    var surname:String?=null,
    var email: String?=null,
   // var password: String?=null,
    var tripsID: List<String?>?=null,
    var ID: String?=null

) {

    // Metoda sprawdza czy Firebase Realtime Database posiada tablice o nazwie <databaseTableName>. Jeżeli nie to wtedy zostaje ona stworzona.
    fun creatingTable(databaseTableName: String = "users", create: Boolean = false) {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val usersRef: DatabaseReference = db.getReference(databaseTableName)
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    usersRef.setValue("");
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToDatabase(databaseTableName: String = "users") {
        creatingTable()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val usersRef: DatabaseReference = db.getReference(databaseTableName)
        val newUserRef = usersRef.push()
        newUserRef.child("name").setValue(name)
        newUserRef.child("surname").setValue(surname)
        newUserRef.child("email").setValue(email)
        //newUserRef.child("password").setValue(password)
        newUserRef.child("tripsID").setValue(tripsID)
        newUserRef.child("ID").setValue(newUserRef.key)
        this.ID = newUserRef.key
    }

    // Aktualizuje dane na podstawie instancji klasy
    fun updateUserByID(ID: String? = this.ID, databaseTableName: String = "users") {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference(databaseTableName)
        val userRef = usersRef.child(ID.toString())
        val updates = HashMap<String, Any>()

        updates["name"] = this.name.toString()
        updates["surname"] = this.surname.toString()
        updates["email"] = this.email.toString()
        //updates["password"] = this.password.toString()
        updates["tripsID"] = this.tripsID.toString()
        userRef.updateChildren(updates)  // <-- dodaje aktualizacje do bazy danych (JEST ASYNCHRONICZNĄ!!!)
    }

    // FUNKCJA ASYNCHRONICZNA (Wyszukuje po ID użytkownika i przepisuje te wartości do tej instancji)
    fun findUserById(Id: String, databaseTableName: String = "users") {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference(databaseTableName)
        val userRef = usersRef.child(Id)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    name = user.name
                    surname = user.surname
                    email = user.email
                    //password = user.password
                    tripsID = user.tripsID
                    ID = Id
                }

                Log.e("LIST_TRIPS_ID", tripsID?.get(0) ?: "Nothing")
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })


    }
}



/**
    private lateinit var name:String
    private lateinit var lastname:String
    private lateinit var pesel:String
    private var phoneNumber:Int=0
    private lateinit var password:String
    private lateinit var email:String


    fun setEmail(email:String){
        this.email=email
    }
    fun getEmail():String{
        return email
    }

    fun setPhoneNumber(phoneNumber: Int) {
        this.phoneNumber = phoneNumber
    }

    fun getPhoneNumber(): Int {
        return phoneNumber
    }


    fun setPassword(password: String) {
        val hash_password = SHA256().SHA256(password)
        this.password = hash_password
    }

    fun setName(name:String){
        this.name=name;
    }

    fun setLastname(lastname:String){
        this.lastname=lastname
    }
/**
    //default validator false, enables validation of PESEL number (function check_pesel())
    fun setPesel(pesel:String, validator: Boolean =false){

        this.pesel = pesel
        if(validator)
            if (!check_pesel()) {
                throw Exception("Pesel is not correct")
                this.pesel = null.toString()
            }

    }
**/
    fun getName(): String {
        return name
    }

    fun getLastname(): String {
        return lastname
    }
/**
    fun getPesel(): String {
        return pesel
    }
**/
/**
    private fun check_pesel():Boolean{

            var weight = arrayOf(1,3,7,9,1,3,7,9,1,3)
            val peselListInt = pesel.toList().map { it.toString().toInt() }
            var control_sum = 0

            if(pesel.length==11){
                for(i in 0..9) control_sum += peselListInt[i]*weight[i]
                if(10-(control_sum%10)==peselListInt[10]) return true
            }
            return false
        }
**/
    private fun check_email():Boolean{
        //todo

        return true
    }
**/
//}


