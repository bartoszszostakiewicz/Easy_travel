package com.project.easy_travel




class User {

    private lateinit var name:String
    private lateinit var lastname:String
    private lateinit var pesel:String
    //private lateinit var login:String


    fun setName(name:String){
        this.name=name;
    }

    fun setLastname(lastname:String){
        this.lastname=lastname
    }

    fun setPesel(pesel:String){
            this.pesel=pesel
        if(!check_pesel()){
            throw Exception("Pesel is not correct")
            this.pesel= null.toString()
        }

    }

    fun getName(): String {
        return name
    }

    fun getLastname(): String {
        return lastname
    }

    fun getPesel(): String {
        return pesel
    }


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


}


