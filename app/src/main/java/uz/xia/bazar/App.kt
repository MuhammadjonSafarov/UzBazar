package uz.xia.bazar

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.yandex.mapkit.MapKitFactory
import uz.xia.bazar.common.YANDEX_MAPKIT_API_KEY

class App :Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MapKitFactory.setApiKey(YANDEX_MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
    }
}