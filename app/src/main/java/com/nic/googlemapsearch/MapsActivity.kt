package com.nic.googlemapsearch

import android.Manifest
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.internal.GoogleApiManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.nic.googlemapsearch.Fragement.AddDataFragment
import com.nic.googlemapsearch.Fragement.ListOfdataFragment
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import com.nic.googlemapsearch.databinding.ActivityMapsBinding
import io.realm.Realm


@RequiresApi(Build.VERSION_CODES.R)
class MapsActivity(

) : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener, GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener,
    GoogleMap.OnMapLoadedCallback,
    GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener {
    val TAG = MapsActivity::class.java.name
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var locationRequest: LocationRequest
    lateinit var locationRequest2: com.google.android.gms.location.LocationRequest
    lateinit var googleApi: GoogleApiManager
    val INTERVAL = (1000 * 1).toLong()
    val FASTEST_INTERVAL = (1000 * 1).toLong()
    private var mCurrentLocation: Location? = null


    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    var latLng: LatLng? = null
    // private var gnssStatusListener: GNSSStatusListener? = null

    lateinit var realm: Realm
    //20.7580154,72.1113358

    private val bondiLocation: CameraPosition = CameraPosition.Builder()
        .target(LatLng(20.7580154, 72.1113358))
        .zoom(5.0f)
        .bearing(350f)
        .tilt(0f)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        realm = Realm.getDefaultInstance()
        supportActionBar.apply { title = "Location" }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = LocationListener {

        }

        // gnssStatusListener = GNSSStatusListener()


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        // createlocationrequest()


        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(applicationContext)

        mapFragment.getMapAsync(this)

        binding.fbAdd.setOnClickListener {
            if (latLng != null) {
                Utill(applicationContext).setFragment(
                    AddDataFragment(supportActionBar, latLng),
                    false,
                    this,
                    R.id.nav_host_fragment_content_home
                )

            } else {
                Utill(applicationContext).showmassage(
                    applicationContext,
                    binding.fbAdd,
                    "please select your location"
                )
            }

        }
        binding.fbList.setOnClickListener(View.OnClickListener {
            if (realm.where(AddressBookinfo::class.java).findAll().size > 0) {
                Utill(applicationContext).setFragment(
                    ListOfdataFragment(supportActionBar),
                    false,
                    this,
                    R.id.nav_host_fragment_content_home
                )

            } else {
                Utill(applicationContext).showmassage(this, binding.fbList, "Please add Task")
            }

        })


    }

    override fun onStart() {


        super.onStart()
    }

    private fun createlocationrequest() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        locationRequest = LocationRequest.Builder(INTERVAL).build().apply {
            INTERVAL
            FASTEST_INTERVAL
            LocationRequest.QUALITY_HIGH_ACCURACY

        }
    } else {


    }




    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.clear()
        mMap = googleMap

        // Add a marker in Sydney and move the camera


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            return
        }
        mMap.isMyLocationEnabled = true
        mMap.isTrafficEnabled = true
        mMap.isIndoorEnabled = true
        mMap.isBuildingsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMapLoadedCallback(this)


    }


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            with(it) {


                when {
                    it.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        Log.e("TAG", ":ACCESS_FINE_LOCATION ")

                    }
                    it.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                        Log.e("TAG", ":ACCESS_COARSE_LOCATION ")

                        // Only approximate location access granted.
                    }
                    else -> {

                        Log.e("TAG", ": no Permission granyed")
                        // No location access granted.
                    }
                }

            }


        }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btn_add) {
            Log.e(TAG, "onClick: ")

        } else if (v.id == R.id.fb_list) {

        }

    }

    override fun onCameraMove() {
        Log.e(TAG, "onCameraMove: ")

    }

    override fun onCameraMoveStarted(p0: Int) {
        when (p0) {
            GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {

                Log.e(TAG, "onMapReady: REASON_GESTURE")
            }
            GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION -> {

                Log.e(TAG, "onMapReady: REASON_DEVELOPER_ANIMATION")

            }
            GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION -> {

                Log.e(TAG, "onMapReady: REASON_API_ANIMATION")

            }

        }
    }

    override fun onCameraIdle() {
        val position = mMap.cameraPosition.target
        latLng = LatLng(position.latitude, position.longitude)
    }

    override fun onMapLoaded() {


        val cameraPosition = mMap.cameraPosition.target

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(bondiLocation))

        mMap.setOnMyLocationClickListener(this)
        mMap.setOnMapClickListener(this)




        mMap.setOnCameraMoveStartedListener(this)


        mMap.setOnCameraIdleListener(this)




        Log.e(TAG, "onMapLoaded: ${mMap.maxZoomLevel}")

        Log.e(TAG, "onMapLoaded: ${mMap.minZoomLevel}")
        Log.e(TAG, "onMapLoaded:    ${cameraPosition.latitude} ${cameraPosition.longitude}")

    }

    override fun onMyLocationClick(p0: Location) {
        latLng = LatLng(p0.latitude, p0.longitude)
        mCurrentLocation = p0
        with(p0) {
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        p0.latitude,
                        p0.longitude
                    ), 15.5f
                )
            )
        }


    }

    override fun onMapClick(p0: LatLng) {
        mMap.clear()
        latLng = LatLng(p0.latitude, p0.longitude)

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    p0.latitude,
                    p0.longitude
                ), 15.5f
            )
        )

        mMap.addMarker(
            MarkerOptions().position(p0)
        )?.showInfoWindow()




        Log.e(TAG, "onMapClick: ${p0}")


    }





    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.findItem(R.id.deleteitom)?.setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home ->

                onBackPressed()

            R.id.deleteitom -> {

                if(   Utill(applicationContext).Deletaitem(applicationContext,realm)){

                }

            }


        }
        return true
    }


    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}


