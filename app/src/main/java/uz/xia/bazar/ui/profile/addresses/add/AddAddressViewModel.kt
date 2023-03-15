package uz.xia.bazar.ui.profile.addresses.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

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