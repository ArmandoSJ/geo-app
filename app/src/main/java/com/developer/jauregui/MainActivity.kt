package com.developer.jauregui

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cursosant.android.stores.editModule.viewModel.EditRutaViewModel
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.databinding.ActivityMainBinding
import com.developer.jauregui.mainmodel.RutasActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.util.*

const val TITLE = "GEO MAP"
class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private lateinit var mapView: GoogleMap

    private lateinit var mainViewBinding: ActivityMainBinding
    private lateinit var mRutaEntity: RutaEntity
    private lateinit var mSaveRutaViewModel: EditRutaViewModel
    // inside a basic activity
    private var locationManager : LocationManager? = null

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

    private fun setupViewModel() {

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

    private var markers : MutableList<Marker> = mutableListOf()


    override fun onMapReady(googleMap: GoogleMap) {
        mapView = googleMap
        //elimina los marcadores del map
        mapView.setOnInfoWindowClickListener { marketToDelete ->
            markers.remove(marketToDelete)
            marketToDelete.remove()
        }
        // Create persistent LocationManager reference
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        mapView.setOnMapLongClickListener { latLng ->

            showAlertDialog(latLng)
        }

        enableLocation()
    }

    private fun showAlertDialog(latLng: LatLng) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.create_dialog,null)
        val dialog = AlertDialog.Builder(this).setTitle("Crear direccion")
            .setView(placeFormView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("Agregar", null)
            .show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val vDireccion = placeFormView.findViewById<EditText>(R.id.edtDirrecion).text.toString()
            val vFecha = placeFormView.findViewById<EditText>(R.id.edtFecha).text.toString()
            val marker = mapView.addMarker(MarkerOptions().position(latLng)
                .title(vDireccion).snippet(vFecha))


                with(mRutaEntity) {
                    vDescription = vDireccion
                    vLong = latLng.longitude.toString()
                    vLat = latLng.latitude.toString()
                    vUserID = "asalazarj"
                    //vFechaEntre = vFecha
                }
            mSaveRutaViewModel.saveRuta(mRutaEntity)



            markers.add(marker)

            dialog.dismiss()
        }

    }


    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val zoomLevel = 17f
            val positionView = LatLng(location.latitude, location.longitude)
            // latLng contains the coordinates where the marker is added
            mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(positionView, zoomLevel))
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }



    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if (!::mapView.isInitialized) return
        if (isLocationPermissionGranted()) {
            mapView.isMyLocationEnabled = true
            try {
                // Request location updates
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
            } catch(ex: SecurityException) {
                ex.printStackTrace()
                Log.d("no location", "location no found")
            }
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
        return true
    }
}