package uz.xia.bazar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.xia.bazar.common.Status
import uz.xia.bazar.data.Category
import uz.xia.bazar.data.Restaurant
import uz.xia.bazar.network.NetworkManager

interface IHomeViewModel {
    fun loadCategories()
    fun loadRestaurants()
    fun loadVerticalRestaurants()
    val liveCatData: LiveData<List<Category>>
    val liveCatStatus: LiveData<Status>

    val liveRestaurantData: LiveData<List<Restaurant>>
    val liveRestaurantStatus: LiveData<Status>

    val liveRestaurantVData: LiveData<List<Restaurant>>
    val liveRestaurantVStatus: LiveData<Status>
}

class HomeViewModel : ViewModel(), IHomeViewModel {

    override val liveCatData = MutableLiveData<List<Category>>()
    override val liveCatStatus = MutableLiveData<Status>()

    override val liveRestaurantData = MutableLiveData<List<Restaurant>>()
    override val liveRestaurantStatus = MutableLiveData<Status>()

    override val liveRestaurantVData = MutableLiveData<List<Restaurant>>()
    override val liveRestaurantVStatus = MutableLiveData<Status>()
    private val apiService = NetworkManager.getInstaince()

    override fun loadCategories() {
        viewModelScope.launch {
            try {
                liveCatStatus.postValue(Status.LOADING)
                delay(1_000L)
                val res = apiService.getCategories()
                if (res.isSuccessful) {
                    liveCatData.postValue(res.body())
                    liveCatStatus.postValue(Status.SUCCESS)
                }
            } catch (e: Exception) {
                liveCatStatus.postValue(Status.ERROR(e.toString()))
            }
        }
    }

    override fun loadRestaurants() {
        viewModelScope.launch {
            try {
                liveRestaurantStatus.postValue(Status.LOADING)
                delay(1_000L)
                val res = apiService.getRestaurants()
                if (res.isSuccessful) {
                    liveRestaurantStatus.postValue(Status.SUCCESS)
                    liveRestaurantData.postValue(res.body())
                }
            } catch (e: Exception) {
                liveRestaurantStatus.postValue(Status.ERROR(e.toString()))
            }
        }
    }

    override fun loadVerticalRestaurants() {
        viewModelScope.launch {
            try {
                liveRestaurantVStatus.postValue(Status.LOADING)
                delay(1_000L)
                val res = apiService.getRestaurants()
                if (res.isSuccessful) {
                    liveRestaurantVStatus.postValue(Status.SUCCESS)
                    liveRestaurantVData.postValue(res.body())
                }
            } catch (e: Exception) {
                liveRestaurantVStatus.postValue(Status.ERROR(e.toString()))
            }
        }
    }
}