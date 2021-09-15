package com.willvargas.telemetria_esp8266.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.willvargas.telemetria_esp8266.R

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        googleMap.setMinZoomPreference(11.0f);
        val udea = LatLng(6.268046507102092, -75.56877723060536)
        googleMap.addMarker(MarkerOptions().position(udea).title("UdeA"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(udea))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}