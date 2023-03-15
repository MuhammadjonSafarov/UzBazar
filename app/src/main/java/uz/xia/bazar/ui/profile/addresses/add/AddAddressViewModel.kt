package uz.xia.bazar.ui.profile.addresses.add

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

interface IAddAddressViewModel {
    fun loadAddress(name: String)
}

private const val TAG = "AddAddressViewModel"

class AddAddressViewModel(app: Application) : AndroidViewModel(app), IAddAddressViewModel {

    override fun loadAddress(name: String) {
        viewModelScope.launch {
            try {

            }catch (e:Exception) {
                Timber.d("$TAG Exception ${e.message}")
            }

        }
    }

}