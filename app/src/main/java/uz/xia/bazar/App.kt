package uz.xia.bazar

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.yandex.mapkit.MapKitFactory
import timber.log.Timber
import uz.xia.bazar.common.YANDEX_MAPKIT_API_KEY

class App :Application() {

    override fun onCreate() {
        super.onCreate()
        context=this
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MapKitFactory.setApiKey(YANDEX_MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
    }

    companion object{
        var context:Context?=null
    }
}