
package com.unifor.sistemadeembarque

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions



    class MapaDaRota : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
        private lateinit var map: GoogleMap
        private lateinit var fusedLocationClient: FusedLocationProviderClient
        private lateinit var lastLocation: Location

        // [START_EXCLUDE]
        // [START maps_marker_get_map_async]
        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            // Retrieve the content view that renders the map.
            setContentView(R.layout.activity_mapa_da_rota)

            // Get the SupportMapFragment and request notification when the map is ready to be used.
            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            mapFragment?.getMapAsync(this)



            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        }
        // [END maps_marker_get_map_async]
        // [END_EXCLUDE]

        // [START maps_marker_on_map_ready_add_marker]
        override fun onMapReady(googleMap: GoogleMap) {
            map = googleMap
            map.uiSettings.isZoomControlsEnabled = true

            /*map.setOnMarkerClickListener(this)*/
            map.setOnMarkerClickListener { marker ->
                if (marker.isInfoWindowShown) {
                    marker.hideInfoWindow()
                } else {
                    marker.showInfoWindow()
                }
                true
            }


            setUpMap()
            // [END_EXCLUDE]
        }

        private fun setUpMap() {
            map.isMyLocationEnabled = true

// 2
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                // 3
                if (location != null) {
                    lastLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    placeMarkerOnMap(currentLatLng)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
        private fun placeMarkerOnMap(location: LatLng) {
            // 1
            val markerOptions = MarkerOptions().position(location)
            // 2
            map.addMarker(markerOptions)
        }

        override fun onMarkerClick(p0: Marker): Boolean {
            TODO("Not yet implemented")
        }


        // [END maps_marker_on_map_ready_add_marker]
    }
// [END maps_marker_on_map_ready]





