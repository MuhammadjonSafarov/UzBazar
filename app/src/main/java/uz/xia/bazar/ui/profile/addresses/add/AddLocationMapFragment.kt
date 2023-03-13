package uz.xia.bazar.ui.profile.addresses.add

import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import uz.xia.bazar.R
import uz.xia.bazar.databinding.FragmentAddAddressMapBinding
import uz.xia.bazar.utils.getBitmapFromVector
import uz.xia.bazar.utils.lazyFast

class AddLocationMapFragment:Fragment(), UserLocationObjectListener {

    lateinit var mapView: MapView
    private var userLocationLayer: UserLocationLayer? = null

    private val markIcon by lazyFast {
        ImageProvider.fromBitmap(requireContext().getBitmapFromVector(R.drawable.home_marker))
    }
    private val animation = Animation(Animation.Type.SMOOTH, 2.5f)
    private var _binding: FragmentAddAddressMapBinding? = null
    private val binding get() = _binding!!
    private val navController by lazyFast {
        Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment_main
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAddressMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMap()
        moveInitCamera()
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

    private fun moveInitCamera() {
        val homePoint = Point(41.311082, 69.281944)
        /*     binding.mapview.map.move(
                 CameraPosition(homePoint, 14.0f, 0.0f, 0.0f),
                 Animation(Animation.Type.SMOOTH, 1f),
                 null
             )*/

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
}