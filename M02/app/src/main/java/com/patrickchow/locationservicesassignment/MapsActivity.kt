package com.patrickchow.locationservicesassignment

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // YOU COULD uncomment this to have the map automatically go to the current location
        //requestPermission()

        // Move to current location. Needs location permission though.
        btn_position.setOnClickListener {
            requestPermission()
        }

        // Adds a mark based off of the camera's position to the map
        btn_pin.setOnClickListener {
            val latLng = mMap.cameraPosition.target
            mMap.addMarker(MarkerOptions().position(latLng).title("Your Pin"))
        }
    }

    //Request for permission for location
    fun requestPermission(){
        ActivityCompat
            .requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                111)
    }

    // If permission is okay, move to the current location of device
    fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            val locationProvider = LocationServices.getFusedLocationProviderClient(this)

            // Listener here because getting location takes time
            locationProvider.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val latLng = LatLng(it.latitude, it.longitude)

                    //Moves the camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                    //Places a mark on the location
                    mMap.addMarker(MarkerOptions().position(latLng).title("Your Location"))
                }
            }

        }
    }

    // If request is okay, move camera to getCurrentLocation()
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111) {
            getCurrentLocation()
        }
    }
}
