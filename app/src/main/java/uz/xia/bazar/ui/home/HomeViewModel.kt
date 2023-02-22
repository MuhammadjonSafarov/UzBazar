package uz.xia.bazar.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.xia.bazar.common.Status
import uz.xia.bazar.data.Category
import uz.xia.bazar.network.NetworkManager

interface IHomeViewModel {
    fun loadCategories()
    val liveData: LiveData<List<Category>>
    val liveStatus: LiveData<Status>
}

class HomeViewModel(app: Application) : AndroidViewModel(app), IHomeViewModel {
    override val liveData = MutableLiveData<List<Category>>()

    override val liveStatus = MutableLiveData<Status>()

    private val apiService = NetworkManager.getInstaince(app)

    override fun loadCategories() {
        viewModelScope.launch {
            try {
                liveStatus.postValue(Status.LOADING)
                delay(1_000L)
                val res = apiService.getCategories()
                if (res.isSuccessful) {
                    liveStatus.postValue(Status.SUCCESS)
                    liveData.postValue(res.body())
                }
            } catch (e: Exception) {
                liveStatus.postValue(Status.ERROR(e.toString()))
            }
        }
    }


}