
package com.unifor.sistemadeembarque

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
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
import java.io.IOException


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

           //val titleStr = getAddress(location)  // add these two lines
            markerOptions.title("demonstração")

            map.addMarker(markerOptions)
        }

        /*private fun getAddress(latLng: LatLng): String {
            // 1
            val geocoder = Geocoder(this)
            val addresses: List<Address>?
            val address: Address?
            var addressText = ""

            try {
                // 2
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                // 3
                if (null != addresses && !addresses.isEmpty()) {
                    address = addresses[0]
                    for (i in 0 until address.maxAddressLineIndex) {
                        addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                    }
                }
            } catch (e: IOException) {
                Log.e("MapsActivity", e.localizedMessage)
            }

            return addressText
        }*/

        override fun onMarkerClick(p0: Marker): Boolean {
            TODO("Not yet implemented")
        }


        // [END maps_marker_on_map_ready_add_marker]
    }
// [END maps_marker_on_map_ready]





