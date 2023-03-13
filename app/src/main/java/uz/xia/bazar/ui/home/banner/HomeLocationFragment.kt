package uz.xia.bazar.ui.home.banner

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import uz.xia.bazar.R
import uz.xia.bazar.databinding.FragmentMapHomeViewBinding
import uz.xia.bazar.utils.getBitmapFromVector
import uz.xia.bazar.utils.lazyFast


class HomeLocationFragment : Fragment(), UserLocationObjectListener {
    private var _binding: FragmentMapHomeViewBinding? = null
    private val binding get() = _binding!!
    private var userLocationLayer: UserLocationLayer? = null

    private val markIcon by lazyFast {
        ImageProvider.fromBitmap(requireContext().getBitmapFromVector(R.drawable.home_marker))
    }
    private val animation = Animation(Animation.Type.SMOOTH, 2.5f)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapHomeViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMap()
        moveInitCamera()
    }


    private fun setUpMap() {
        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(binding.mapview.mapWindow)
        userLocationLayer?.apply {
            isVisible = true
            isHeadingEnabled = true
            setObjectListener(this@HomeLocationFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstaince(): HomeLocationFragment {
            return HomeLocationFragment()
        }
    }

    override fun onObjectAdded(p0: UserLocationView) {


    }

    override fun onObjectRemoved(p0: UserLocationView) {

    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }
}