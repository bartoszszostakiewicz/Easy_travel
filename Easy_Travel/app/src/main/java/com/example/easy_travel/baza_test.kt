package com.example.easy_travel

import java.sql.DriverManager
import java.sql.SQLException


    fun main() {
        val jdbcURL = "jdbc:sqlserver://mysqlserverpow.database.windows.net:1433"

        try {
            val connection =
                DriverManager.getConnection(jdbcURL, "sb49417@mysqlserverpow", "Easy_travel");
            println("Connected!");
        } catch (e: SQLException) {

            println(e.message)
        }
    }
