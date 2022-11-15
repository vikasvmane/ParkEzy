package com.whiterwalkers.parkezy.ui.home

import android.Manifest
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.ui.fragments.ParkInfoBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapsFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: MapViewModel
    private val TAG = MapsFragment::class.java.simpleName
    private var isMapTouched = false
    private lateinit var currentLocation: Location
    private lateinit var pinLayout: LinearLayout
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        this.googleMap = googleMap
        setupMapListeners()
    }

    private fun setupMapListeners() {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(), R.raw.map_style
            )
        )
        googleMap.setOnCameraIdleListener {
            Log.e(
                TAG,
                "==camera idle==" + googleMap.cameraPosition.target
            )
            if (isMapTouched)
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(500)
                    viewModel.getNearByParkingSpot(
                        com.whiterwalkers.parkezy.model.pojos.Location(
                            googleMap.cameraPosition.target.latitude,
                            googleMap.cameraPosition.target.longitude
                        )
                    )
                }
            isMapTouched = false
        }
        googleMap.setOnCameraMoveStartedListener { reason ->
            when (reason) {
                OnCameraMoveStartedListener.REASON_GESTURE -> {
                    // User drags the amp
                    isMapTouched = true
                    pinLayout.visibility = VISIBLE
                }
                OnCameraMoveStartedListener.REASON_API_ANIMATION -> {
                    // User tapped something on the map.
                    pinLayout.visibility = GONE
                }
                OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION -> {
                    Toast.makeText(
                        activity, "The app moved the camera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        googleMap.setOnMarkerClickListener {
            //Pass marker details to bottom sheet
            Navigation.findNavController(this.requireView())
                .navigate(R.id.action_nav_home_to_nav_bottom_sheet)
//            ParkInfoBottomSheetFragment.newInstance(5).apply {
//                show(activity?.supportFragmentManager!!, ParkInfoBottomSheetFragment.TAG)
//            }
            return@setOnMarkerClickListener false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        pinLayout = view.findViewById(R.id.locationMarker)
        mapFragment?.getMapAsync(callback)
        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()
        viewModel.parkingSpotLiveData.observe(requireActivity()) {
            setNearByParkingSpots(it)
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            when {
                permissions.getOrDefault(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) || permissions.getOrDefault(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {
                    // Precise location access granted.
                    getLastLocation()
                }
                else -> {
                    // No location access granted.
                }
            }
        }
    }

    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                setNewLocation(location)
                if (location != null) {
                    currentLocation = location
                }
            }
    }

    private fun setNewLocation(location: Location?) {
        location?.let {
            if (this::googleMap.isInitialized) {
                val currentLatLng = LatLng(it.latitude, it.longitude)

                googleMap.addMarker(
                    MarkerOptions().position(currentLatLng).title("My location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_2))
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))
            }
        }
    }

    private fun setNearByParkingSpots(listParkingSpot: List<ParkingSpot>) {
        if (this::googleMap.isInitialized) {
            googleMap.clear()
            listParkingSpot.map {
                val location = LatLng(it.location.lat, it.location.lng)
                googleMap.addMarker(
                    MarkerOptions().position(location).title(it.parkingName)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_parking))
                )
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
                        )
                    ).title("My location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_2))
                )
            }
        }
    }
}