package uz.xia.bazar.ui.profile.addresses.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import timber.log.Timber
import uz.xia.bazar.R
import uz.xia.bazar.databinding.FragmentAddAddressMapBinding
import uz.xia.bazar.utils.getBitmapFromVector
import uz.xia.bazar.utils.lazyFast
import java.util.concurrent.Executors

const val REQUEST_CODE_RESOLUTION = 1_001
private const val LOCATION_PERMISSION_REQ_CODE = 1001
private const val REQ_ACCESS_FINE_LOCATION = 2_020
private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 20_000L
private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
const val REQUEST_CODE_UPDATE = 101
private const val TAG = "AddLocationMapFragment"

class AddLocationMapFragment : Fragment(), UserLocationObjectListener, View.OnClickListener,
    OnSuccessListener<LocationSettingsResponse>, OnFailureListener {

    private var hasEnableLocation = false
    private val settingsClient by lazyFast { LocationServices.getSettingsClient(requireContext()) }
    private var requestingLocationUpdates: Boolean = false
    private var locationRequest: LocationRequest? = null
    private val executor = Executors.newSingleThreadExecutor()
    private val locationSettingsRequest by lazyFast {
        LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!).build()
    }
    private var currentLocation: Location? = null
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazyFast {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    // lateinit var mapView: MapView
    private var userLocationLayer: UserLocationLayer? = null
    private val markIcon by lazyFast {
        ImageProvider.fromBitmap(requireContext().getBitmapFromVector(R.drawable.home_marker))
    }
    private val animation = Animation(Animation.Type.SMOOTH, 2.5f)
    private var _binding: FragmentAddAddressMapBinding? = null
    private val binding get() = _binding!!
    private val navController by lazyFast {
        Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment_main
        )
    }
    private val viewModel by viewModels<AddAddressViewModel>()
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onLocation()
        } else {
            //TODO not fount
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            currentLocation = locationResult.lastLocation
            requestingLocationUpdates = true
            if (hasEnableLocation) {
                hasEnableLocation = false
                moveInitCamera(
                    Point(
                        currentLocation?.latitude ?: 0.0,
                        currentLocation?.longitude ?: 0.0
                    )
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAddressMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpMap()
        //moveInitCamera()
    }

    private fun setUpViews() {
        binding.toolbar.setNavigationOnClickListener(this)
        binding.location.setOnClickListener(this)
        binding.etAddress.addTextChangedListener {
            if (it != null && it.length > 3){
                viewModel.loadAddress(it.toString())
            }
        }
    }


    private fun setUpMap() {
        userLocationLayer =
            MapKitFactory.getInstance().createUserLocationLayer(binding.mapview.mapWindow)
        userLocationLayer?.apply {
            isVisible = true
            isHeadingEnabled = true
            setObjectListener(this@AddLocationMapFragment)
        }
        binding.mapview.map?.apply {
            logo.setAlignment(
                Alignment(
                    HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM
                )
            )
        }
    }

    private fun moveInitCamera(homePoint: Point) {
        binding.mapview.map.mapObjects.addPlacemark(
            homePoint,
            markIcon,
            IconStyle(PointF(0.5f, 0.5f), RotationType.NO_ROTATION, 1f, false, true, 1f, null)
        )
        binding.mapview.map.move(
            CameraPosition(homePoint, 15f, 0f, 0f), animation
        ) {

        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onObjectAdded(p0: UserLocationView) {


    }

    override fun onObjectRemoved(p0: UserLocationView) {

    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }

    override fun onClick(item: View?) {
        when (item?.id) {
            R.id.location -> {
                when {
                    checkLocationPermission() -> {
                        onLocation()
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {

                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }
            R.id.toolbar -> navController.popBackStack()
            else -> {}
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun onLocation() {
        if (locationRequest == null) createLocationRequest()
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    override fun onSuccess(lsres: LocationSettingsResponse?) {
        Timber.i("$TAG All location settings are satisfied.")
        hasEnableLocation = true
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest!!, locationCallback, Looper.getMainLooper()
        )
    }

    override fun onFailure(e: Exception) {
        val statusCode = (e as ApiException).statusCode
        when (statusCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                Timber.i("$TAG Location settings are not satisfied. Attempting to upgrade location settings ")
                try {
                    val rae = e as ResolvableApiException
                    rae.startResolutionForResult(requireActivity(), REQUEST_CODE_RESOLUTION)
                } catch (sie: IntentSender.SendIntentException) {
                    Timber.i("$TAG PendingIntent unable to execute request.")
                }

            }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                Toast.makeText(
                    requireContext(), R.string.error_enable_gps_setting, Toast.LENGTH_LONG
                ).show()
                requestingLocationUpdates = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESOLUTION) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    startLocationUpdates()
                    Timber.i("User agreed to make required location settings changes.")

                }
                Activity.RESULT_CANCELED -> {
                    //   LocationRequestDialog().show(supportFragmentManager, "dialog")
                    Timber.i("User chose not to make required location settings changes.")
                }
            }
        }
    }

    private fun startLocationUpdates() {
        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener(executor, this).addOnFailureListener(executor, this)
    }

}