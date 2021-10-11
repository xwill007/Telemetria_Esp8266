package com.willvargas.telemetria_esp8266.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import kotlinx.android.synthetic.main.card_view_equipos_item.*

class MapsFragment : Fragment() {

    //private val arg: MapsFragment by navArgs()
    private lateinit var equipo : EquiposServer
    private lateinit var IDequipo: String
    private lateinit var latitud:String
    private lateinit var longitud:String
    private lateinit var ubicacion: LatLng


    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        googleMap.setMinZoomPreference(11.0f);
        ubicacion = obtenerUbicacionFirebase()
        googleMap.addMarker(MarkerOptions().position(ubicacion).title("UdeA"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion))
    }

    private fun obtenerUbicacionFirebase(): LatLng {
        //IDequipo = "ESP01" //obtener id
        latitud ="0.0"
        longitud ="-0.0"
        val database = FirebaseDatabase.getInstance()
        val myRefLat = database.getReference().child(IDequipo).child("ubicacion").child("lat")

        myRefLat.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    latitud = dataSnapshot?.getValue().toString()
                    Log.d("Ubicacion lat: ", latitud)
                } else Log.d("Ubicacion lat", "no existe.")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("Ubicacion lat", "Failed to read value.", error.toException())
            }
        })
        val myRefLng = database.getReference().child(IDequipo).child("ubicacion").child("lng")
        myRefLng.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    longitud = dataSnapshot?.getValue().toString()
                    Log.d("Ubicacion lng: ", longitud)
                }else Log.d("Ubicacion lng", "no existe.")
            }override fun onCancelled(error: DatabaseError) {
                Log.w("Ubicacion lng", "Failed to read value.", error.toException())
            }
            })
        return LatLng(latitud.toDouble(),longitud.toDouble() )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //equipo = arg.equipo!!
        //IDequipo = equipo.idEquipo.toString()
        IDequipo = arguments?.getString("IDequipo").toString()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


}