package com.project.easy_travel.ViewModel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.project.easy_travel.Model.*
import com.project.easy_travel.Organizacja
import com.project.easy_travel.R
import com.project.easy_travel.TripListActivity
import java.util.*

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val administrator1 = Administrator("Tomasz", "Wiśniewski", "tomaszwisniewski@example.com", "a1b2c3d4e5f6g7h8i9j10k11l12m13n14o15")
        val administrator2 = Administrator("Katarzyna", "Kowalska", "katarzynakowalska@example.com", "a1b2c3d4e5f6g7h8i9j10k11l12m13n14o15")
        administrator1.addToDatabase()
        administrator2.addToDatabase()

        val trip1 = Trip("Wycieczka po Europie", "Wycieczka po Europie obejmująca wizytę w kilku krajach", administrator1.ID)
        val trip2 = Trip("Wycieczka po Azji", "Wycieczka po Azji obejmująca wizytę w kilku krajach", administrator2.ID)
        val trip3 = Trip("Wycieczka po Afryce", "Wycieczka po Afryce obejmująca wizytę w kilku krajach", administrator1.ID)
        trip1.addToDatabase()
        trip2.addToDatabase()
        trip3.addToDatabase()

        val user1 = User("Jan", "Kowalski", "jankowalski@example.com", "a1b2c3d4e5f6g7h8i9j10k11l12m13n14o15", listOf(trip1.ID, trip2.ID))
        val user2 = User("Anna", "Nowak", "annanowak@example.com", "a1b2c3d4e5f6g7h8i9j10k11l12m13n14o15", listOf(trip3.ID))
        user1.addToDatabase()
        user2.addToDatabase()

        val tripPoint1 = TripPoint(trip1.ID, "Wizyta w Paryżu", "Zwiedzanie Paryża, m.in. Wieży Eiffla", isDone = true)
        val tripPoint2 = TripPoint(trip1.ID, "Wizyta w Londynie", "Zwiedzanie Londynu, m.in. Big Bena", isDone = false)
        val tripPoint3 = TripPoint(trip2.ID, "Wizyta w Tokio", "Zwiedzanie Tokio, m.in. wzgórza Meiji", isDone = true)
        tripPoint1.addToDatabase()
        tripPoint2.addToDatabase()
        tripPoint3.addToDatabase()

        val comment1 = Comment(user1.ID, "Jestem bardzo zadowolony z wycieczki po Europie")
        val comment2 = Comment(user2.ID, "Czy ktoś wie, jak dostać się na Wieżę Eiffla?")
        val comment3 = Comment(administrator1.ID, "Wieża Eiffla jest otwarta dla zwiedzających od godziny 9:00 do 22:00. Wejście można zarezerwować online lub kupić bilet bezpośrednio przed wejściem.")
        val comment4 = Comment(user1.ID, "Jakie atrakcje polecacie w Tokio?")
        val comment5 = Comment(administrator2.ID, "Polecam wizytę w wzgórzach Meiji, parku Disneyland oraz muzeum Ghibli.")
        val comment6 = Comment(user2.ID, "Czy ktoś zna dobry hotel w Paryżu?")
        val comment7 = Comment(administrator1.ID, "Polecam hotel Le Meurice, bardzo dobra lokalizacja i wysoki standard.")
        val comment8 = Comment(user2.ID, "Jestem zachwycony widokiem z Wieży Eiffla!")
        val comment9 = Comment(user1.ID, "Czy ktoś zna dobry przewodnik po Paryżu?")
        val comment10 = Comment(user1.ID, "Czy moglibyśmy wcześniej wyjechać z Paryża?")
        val comment11 = Comment(administrator1.ID, "Będziemy musieli to omówić z pozostałymi uczestnikami wycieczki, ale na pewno spróbuję pomóc.")
        val comment12 = Comment(user2.ID, "Czy będziemy mieć czas na zakupy w Paryżu?")
        comment1.addToDatabase()
        comment2.addToDatabase()
        comment3.addToDatabase()
        comment4.addToDatabase()
        comment5.addToDatabase()
        comment6.addToDatabase()
        comment7.addToDatabase()
        comment8.addToDatabase()
        comment9.addToDatabase()
        comment10.addToDatabase()
        comment11.addToDatabase()
        comment12.addToDatabase()

        val chat1 = Chat(trip1.ID, listOf(comment1.ID, comment2.ID, comment3.ID))
        val chat2 = Chat(trip2.ID, listOf(comment4.ID, comment5.ID))
        val chat3 = Chat(trip3.ID, listOf(comment6.ID, comment7.ID, comment8.ID, comment9.ID))
        chat1.addToDatabase()
        chat2.addToDatabase()
        chat3.addToDatabase()

        val privateChat1 = PrivateChat(trip1.ID, user1.ID, listOf(comment10.ID, comment11.ID))
        val privateChat2 = PrivateChat(trip2.ID, user2.ID, listOf(comment12.ID))
        privateChat1.addToDatabase()
        privateChat2.addToDatabase()
//
//        val user = User()
//        user.findUserById("-NMa_nE5dhu3vco2Lr5Q");



        var username = findViewById<TextView>(R.id.username)
        var password = findViewById<TextView>(R.id.password)

        var btn = findViewById<MaterialButton>(R.id.login_button)
        var register = findViewById<TextView>(R.id.register)

        register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }


        btn.setOnClickListener{
            login(username.text.toString(),password.text.toString());
            //startActivity(Intent(applicationContext, TripListActivity::class.java))
        }

    }

    private fun login(email:String,password:String) {
        var auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {
            if(it.isSuccessful){

                Toast.makeText(this,"Succesfully LoggedIn", Toast.LENGTH_SHORT).show()

                if(email == "organizator123456@gmail.com"){
                    startActivity(Intent(applicationContext, Organizacja::class.java))
                }else{
                    startActivity(Intent(applicationContext, TripListActivity::class.java))
                }

            }else{
                Toast.makeText(this,"Log In failed ", Toast.LENGTH_SHORT).show()
            }
        }

    }

}