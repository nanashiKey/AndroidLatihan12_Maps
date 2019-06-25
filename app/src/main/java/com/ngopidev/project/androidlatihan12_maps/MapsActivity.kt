package com.ngopidev.project.androidlatihan12_maps

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
GoogleMap.OnMarkerClickListener{
    override fun onMarkerClick(p0: Marker?) = false


    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var lastLocation : Location

    var isOpenFAB : Boolean = false

    private lateinit var fab: FloatingActionButton
    private lateinit var fab1: FloatingActionButton
    private lateinit var fab2: FloatingActionButton
    private lateinit var fab3: FloatingActionButton
    private lateinit var fab4: FloatingActionButton

    companion object {
        private const val LOCATION_PERMISSION = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fab = findViewById(R.id.fab)
        fab1 = findViewById(R.id.fab1)
        fab2 = findViewById(R.id.fab2)
        fab3 = findViewById(R.id.fab3)
        fab4 = findViewById(R.id.fab4)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fab.setOnClickListener {
            if(isOpenFAB){
                closeFab()
            }else{
                showFAB()
            }
        }
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
//        val sarkom = LatLng(-6.1645997, 106.7646863)
//        mMap.addMarker(MarkerOptions().position(sarkom).title("Sarang Komodo"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sarkom, 17.0f))
//        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//        val markerOptions = MarkerOptions().position(sarkom)
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
//            BitmapFactory.decodeResource(resources, R.mipmap.ic_mobilmobilan)
//        ))
//        mMap.addMarker(markerOptions.title("Sarang Komodo"))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        settingUpMaps()

    }

    private fun settingUpMaps(){
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION)
            return
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) {
            location ->
            if(location != null){
                lastLocation = location
                val currentPost = LatLng(location.latitude, location.longitude)
                placeMarkerInMaps(currentPost)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPost
                    , 18.0f))
            }
        }

        fab1.setOnClickListener {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            Toast.makeText(applicationContext, "Normal selected", Toast.LENGTH_SHORT).show()
        }
        fab2.setOnClickListener {
            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            Toast.makeText(applicationContext, "Terrain selected", Toast.LENGTH_SHORT).show()
        }
        fab3.setOnClickListener {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            Toast.makeText(applicationContext, "Satellite selected", Toast.LENGTH_SHORT).show()
        }
        fab4.setOnClickListener {
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            Toast.makeText(applicationContext, "Hybrid selected", Toast.LENGTH_SHORT).show()
        }
    }
    fun placeMarkerInMaps(loc : LatLng){
        val markerOptions = MarkerOptions().position(loc)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(resources, R.mipmap.ic_mobilmobilan)
        ))
        mMap.addMarker(markerOptions)
    }

    fun showFAB(){
        isOpenFAB=true
        fab.setImageDrawable(resources.getDrawable(android.R.drawable.arrow_down_float))
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55))
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105))
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155))
        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_205))
    }

    fun closeFab(){
        isOpenFAB=false
        fab.setImageDrawable(resources.getDrawable(android.R.drawable.arrow_up_float))
        fab1.animate().translationY(0f)
        fab2.animate().translationY(0f)
        fab3.animate().translationY(0f)
        fab4.animate().translationY(0f)
    }
}
