package com.project.easy_travel.ViewModel

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.User
import com.project.easy_travel.Model.Wycieczka
import com.project.easy_travel.Model.database

fun writeNewUser(userId: String, name:String,lastname:String, email:String, password:String){




}

fun writeNewTrip(name:String,przewodnik:String, opis:String, key:String){

    val database = Firebase.database.reference

    val trip = Wycieczka(name,przewodnik,opis,key)
    trip.genKey()
    database.child("trip").child(trip.key.toString()).setValue(trip)


}

fun writeNewPoint(lat: Double, lng: Double) {
    //val point = Point(lat, lng)

    val database = Firebase.database.reference
    //database.child("points").push().setValue(point)

}

fun read_points(callback: (MutableList<Point>) -> Unit) {

    val database = Firebase.database.reference
    val listPoints = mutableListOf<Point>()

    database.child("points").get().addOnSuccessListener { data ->
        data.children.forEach { point ->
            val p = point.getValue(Point::class.java)
            Log.i("point12", p?.lat.toString())
            //val newPoint = Point(p?.lat.toString().toDouble(),p?.lng.toString().toDouble())
            //listPoints.add(newPoint)
            Log.i("point13",listPoints.last().toString())
        }

        Log.i("point12","rozmiar zwracanej listy"+listPoints.size.toString())
        Log.i("point11","pierwszy element zwracanej listy" + listPoints[0].toString())

        // Zamiast zwracać listę, przekazujemy ją do funkcji callback
        callback(listPoints)
    }.addOnFailureListener { exception ->
        Log.e("firebase", "Error getting data", exception)
        // W przypadku błędu przekazujemy pustą listę do funkcji callback
        callback(mutableListOf())
    }
}


