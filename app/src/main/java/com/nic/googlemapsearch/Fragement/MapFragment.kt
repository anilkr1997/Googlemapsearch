package com.nic.googlemapsearch.Fragement

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.internal.GoogleApiManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nic.googlemapsearch.R
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import com.nic.googlemapsearch.Utill
import com.nic.googlemapsearch.databinding.ActivityMapsBinding
import com.nic.googlemapsearch.databinding.FragmentMapBinding
import io.realm.Realm

class MapFragment : Fragment() , OnMapReadyCallback, View.OnClickListener, GoogleMap.OnCameraMoveListener,
GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener,
GoogleMap.OnMapLoadedCallback,
GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener{
val  TAG=MapFragment::class.java.name
    private lateinit var mMap: GoogleMap
    private lateinit var mapfragmnetbinding: FragmentMapBinding
    private val binding get() = mapfragmnetbinding!!



    lateinit var locationRequest: LocationRequest
    lateinit var locationRequest2: com.google.android.gms.location.LocationRequest
    lateinit var googleApi: GoogleApiManager
    val INTERVAL = (1000 * 1).toLong()
    val FASTEST_INTERVAL = (1000 * 1).toLong()
    private var mCurrentLocation: Location? = null


    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    var latLng: LatLng? = null

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

    }
    private lateinit var viewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mapfragmnetbinding= FragmentMapBinding.inflate(layoutInflater,container,false)
        return mapfragmnetbinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        realm = Realm.getDefaultInstance()

        // supportActionBar.apply { title = "Location" }
//        locationManager = getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
//        locationListener = LocationListener {
//
//        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment



       // GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context.applicationContext)

        mapFragment.getMapAsync(this)

        binding.fbAdd.setOnClickListener {
            if (latLng != null) {

                val bundle = Bundle()
                bundle.putParcelable("latlng", latLng)
                findNavController().navigate(R.id.action_navigation_homfragemnt_to_navigation_adddatafragment,bundle)

//                Utill(applicationContext).setFragment(
//                    AddDataFragment(supportActionBar, latLng),
//                    false,
//                    this,
//                    R.id.nav_host_fragment_activity_bottem_navigation
//                )

            } else {
                Utill(context?.applicationContext).showmassage(
                    context,
                    binding.fbAdd,
                    "please select your location"
                )
            }

        }
        binding.fbList.setOnClickListener(View.OnClickListener {
            if (realm.where(AddressBookinfo::class.java).findAll().size > 0) {
//                Utill(context).setFragment(
//                    ListOfdataFragment(supportActionBar),
//                    false,
//                    this,
//                    R.id.nav_host_fragment_activity_bottem_navigation
//                )
                val bundle = Bundle()
                bundle.putString("amount", "Anil kumar")
                findNavController().navigate(R.id.action_navigation_homfragemnt_to_navigation_listfragment)


            } else {
                Utill(context).showmassage(context, binding.fbList, "Please add Task")
            }

        })




    }


    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.clear()
        mMap = googleMap

        // Add a marker in Sydney and move the camera


        if (ActivityCompat.checkSelfPermission(
                requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
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
        mMap.addMarker(
            MarkerOptions().position(latLng!!)
        )?.showInfoWindow()

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










    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

}