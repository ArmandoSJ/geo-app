package com.developer.jauregui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.developer.jauregui.databinding.ActivityMainBinding
import com.developer.jauregui.mainmodel.RutasActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

const val TITLE = "GEO MAP"
class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private lateinit var mapView: GoogleMap

    private lateinit var mainViewBinding: ActivityMainBinding

    var currentLocation : Location? = null
    //var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainViewBinding.root)

        supportActionBar?.title = TITLE
        createMapFragment()
        mainViewBinding.fab.setOnClickListener{
            startIntent(
                Intent(this, RutasActivity::class.java)
            )
        }



    }

    private fun startIntent(intent: Intent){
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
        else
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()

    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapView = googleMap
        val boundsBuilder = LatLngBounds.Builder()
        mapView.setOnMapLongClickListener { latLng ->
            mapView.clear();
            // Animating to the touched position
            mapView.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            mapView.addMarker(MarkerOptions().position(latLng)
                .title("Marker").snippet("asd"))
        }



        /*val sydney = LatLng(-34.0, 351.0)
        mapView.addMarker(MarkerOptions().position(sydney).title("asd"))
        mapView.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
        //boundsBuilder.include()
        //mapView.setOnMyLocationButtonClickListener(this)
        enableLocation()
    }


    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if (!::mapView.isInitialized) return
        if (isLocationPermissionGranted()) {
            mapView.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }



    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mapView.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::mapView.isInitialized) return
        if(!isLocationPermissionGranted()){
            mapView.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }
}