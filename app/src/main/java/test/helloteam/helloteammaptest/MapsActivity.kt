package test.helloteam.helloteammaptest

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.provider.Settings;
import androidx.annotation.NonNull;
import java.util.concurrent.TimeUnit


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    //permission integer
    private val MY_PERMISSION_FINE_LOCATION: Int = 44


    //for users location
    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e., how often you
// should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient has a new Location.
    private lateinit var locationCallback: LocationCallback

    // Used only for local storage of the last known location. Usually, this would be saved to your
// database, but because this is a simplified sample without a full database, we only need the
// last location to create a Notification if the user navigates away from the app.
    private var currentLocation: Location? = null

    var userLocationLon = 0.1;
    var userLocationLat = 0.1;



    override fun onCreate(savedInstanceState: Bundle?) {
        //map shit
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //getting user location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if ( ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient!!.lastLocation.addOnSuccessListener {
                //program has permission
                location->
                if(location != null){
                    //update user interface

                }
            }
        }
        else{
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSION_FINE_LOCATION)
        }

        //idk
        //for timing
//        locationRequest = LocationRequest().apply {
//            // Sets the desired interval for active location updates. This interval is inexact. You
//            // may not receive updates at all if no location sources are available, or you may
//            // receive them less frequently than requested. You may also receive updates more
//            // frequently than requested if other applications are requesting location at a more
//            // frequent interval.
//            //
//            // IMPORTANT NOTE: Apps running on Android 8.0 and higher devices (regardless of
//            // targetSdkVersion) may receive updates less frequently than this interval when the app
//            // is no longer in the foreground.
//            interval = TimeUnit.SECONDS.toMillis(60)
//
//            // Sets the fastest rate for active location updates. This interval is exact, and your
//            // application will never receive updates more frequently than this value.
//            fastestInterval = TimeUnit.SECONDS.toMillis(30)
//
//            // Sets the maximum time when batched location updates are delivered. Updates may be
//            // delivered sooner than this interval.
//            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
//
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions,grantResults)
        when(requestCode){
            MY_PERMISSION_FINE_LOCATION ->
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission granted
                }
                else{
                    //permission denited
                    Toast.makeText(applicationContext, "App requires location permission to be granted", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }

    /**
     * Manipulates the map once available
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //adding markers
        //find a way to customize the marker icon

        val userlocation = LatLng(userLocationLon, userLocationLat)
        val toronto = LatLng(43.6532, 79.3832)
        val mississauga = LatLng(79.6441, 43.6532)

        val sydney = LatLng(-34.0, 151.0)

        mMap.addMarker(MarkerOptions().position(toronto).title("Toronto"))
        mMap.addMarker(MarkerOptions().position(sydney).title("Sydney"))
        mMap.addMarker(MarkerOptions().position(mississauga).title("Mississauga"))


        mMap.addMarker(MarkerOptions().position(userlocation).title("User Location"))
    }





}