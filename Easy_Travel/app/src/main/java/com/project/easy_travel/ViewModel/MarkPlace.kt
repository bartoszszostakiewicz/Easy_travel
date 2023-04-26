package com.project.easy_travel.ViewModel

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.Point
import com.project.easy_travel.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mark_place.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarkPlace : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder
    private lateinit var placeNameEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_mark_place, container, false)
        geocoder = Geocoder(requireContext())
        view.findViewById<MaterialButton>(R.id.addPlaceButton).setOnClickListener {

            var placeNameEditText = view.findViewById<EditText>(R.id.placeNameEditText)
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            getLocationAndSaveToFirebase(placeNameEditText.text.toString())
        }


        return view
    }

    private fun getLocationAndSaveToFirebase(placeName: String) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val LOCATION_PERMISSION_REQUEST_CODE=1000
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }


        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val coordinates = geocoder.getFromLocationName(placeName, 1)
                Log.d("MarkPlace", "coordinates: $coordinates")
                if (coordinates != null) {
                    if (coordinates.isNotEmpty()) {
                        val placeLocation = coordinates?.get(0)
                        if (placeLocation != null) {
                            writeNewPoint(placeLocation.latitude, placeLocation.longitude)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Nie znaleziono miejsca", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }





}