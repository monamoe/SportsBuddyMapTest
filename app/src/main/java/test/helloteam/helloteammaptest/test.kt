package test.helloteam.helloteammaptest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class test : AppCompatActivity() {
    // initializing
    // FusedLocationProviderClient
    // object
    var mFusedLocationClient: FusedLocationProviderClient? = null

    // Initializing other items
    // from layout file
    var PERMISSION_ID = 44
    var userLocationLon = 0.1
    var userLocationLat = 0.1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // method to get the location
        lastLocation
    }// if permissions aren't available,
    // request for permissions
// getting last
    // location from
    // FusedLocationClient
    // object
// check if location is enabled

    // check if permissions are given
    @get:SuppressLint("MissingPermission")
    private val lastLocation: Unit
        private get() {
            // check if permissions are given
            if (checkPermissions()) {

                // check if location is enabled
                if (isLocationEnabled) {

                    // getting last
                    // location from
                    // FusedLocationClient
                    // object
                    mFusedLocationClient!!.lastLocation
                        .addOnCompleteListener { task ->
                            val location = task.result
                            if (location == null) {
                                requestNewLocationData()
                            } else {
                                userLocationLat = location.latitude
                                userLocationLon = location.longitude
                            }
                        }
                } else {
                    Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG)
                        .show()
                    val intent =
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            } else {
                // if permissions aren't available,
                // request for permissions
                requestPermissions()
            }
        }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            userLocationLat = mLastLocation.latitude
            userLocationLon = mLastLocation.longitude
        }
    }

    // method to check for permissions
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    // method to check
    // if location is enabled
    private val isLocationEnabled: Boolean
        private get() {
            val locationManager =
                getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

    // If everything is alright then
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                lastLocation
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            lastLocation
        }
    }
}