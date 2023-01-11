package com.project.easy_travel.Model

import com.project.easy_travel.ViewModel.SHA256


data class User(val name: String? = null,val lastname:String?=null, val email: String?=null, val password: String?=null )
{


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
}


