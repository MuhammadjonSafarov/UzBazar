package uz.xia.bazar.geo

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import uz.xia.bazar.R


class GeoLocationFragment : Fragment() {

    lateinit var mapView: MapView
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("43790952-2b06-4f86-b031-5039a3fd1ea7")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)

        mapView=findViewById(R.id.mapview)
        mapView.map.move(
            CameraPosition(Point(41.371785, 69.283904), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 10f), null)
        var mapKit: MapKit = MapKitFactory.getInstance()
        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance()
        super.onStart()
    }
}