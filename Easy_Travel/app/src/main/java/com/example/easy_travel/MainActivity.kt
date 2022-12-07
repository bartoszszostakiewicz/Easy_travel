package com.example.easy_travel

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.sql.Connection
import java.sql.DriverManager

import java.sql.SQLException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username = findViewById<TextView>(R.id.username)
        var password = findViewById<TextView>(R.id.password)
        var btn = findViewById<MaterialButton>(R.id.login_button)
        //admin and admin

        btn.setOnClickListener {
        Log.d("TAD", "Button clicked")


            Log.d("TAD", "Connection established");
            if (username.text.toString() == "admin" || password.text.toString() == "") {
                var mes1 = Toast.makeText(applicationContext, "LOGIN SUCCESFUL", Toast.LENGTH_LONG)
                mes1.show()

                var nowaAktywnosc: Intent = Intent(applicationContext, Menu::class.java)
                startActivity(nowaAktywnosc)
            } else {
                var mes2 = Toast.makeText(applicationContext, "LOGIN FAILED", Toast.LENGTH_LONG)
                mes2.show()
            }
        }

    }
}

fun connectionClass(): Connection? {
    Log.d("TAD", "ConnectionClass")
    var policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    var connection: Connection? = null
    var ConnURL: String? = null
    try {
        ConnURL = "jdbc:sqlserver://mysqlserverpow.database.windows.net:1433;database=Easy_travel_db;user=sb49417@mysqlserverpow;password=Easy_travel;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")

        connection =
            DriverManager.getConnection(ConnURL)
        Log.d("TAD", "Połączono")
    } catch (se: SQLException) {
        se.message?.let { Log.e("ERROR", it) }
    } catch (e: ClassNotFoundException) {
        e.message?.let { Log.e("ERROR", it) }
    } catch (e: Exception) {
        e.message?.let { Log.e("ERROR", it) }


    }
        return connection
}

fun checkLogin(username: String, password: String): Boolean {
    /*
    Log.d("TAD", "CheckLogin")
    var connection: Connection? = null
    var result: Boolean = false
    try {
        connection = connectionClass()
        if (connection == null) {
            Log.d("TAD", "Connection is null")
            result = false
        } else {
            /**
            var query: String =
            "select * from [dbo].[logowanie] where imie = '$username' and nazwisko = '$password'"
            var stmt = connection!!.createStatement()
            var rs = stmt.executeQuery(query)
            if (rs.next()) {
            result = true
            } else {
            result = false
            }
             */
            result = true
        }
    } catch (ex: Exception) {
        with(ex) { printStackTrace() }
    }

     */
    return false
}


