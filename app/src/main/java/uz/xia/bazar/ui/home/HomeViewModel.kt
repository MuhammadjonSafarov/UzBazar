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
                liveStatus.postValue(Status.SUCCESS)
                val categories= listOf(
                    Category(1,"Новые продукты",""),
                    Category(2,"Новые продукты",""),
                    Category(3,"Новые продукты",""),
                    Category(4,"Новые продукты",""),
                    Category(5,"Новые продукты",""),
                    Category(6,"Новые продукты",""),
                    Category(7,"Новые продукты",""),
                    Category(8,"Новые продукты",""),
                    Category(9,"Новые продукты",""),
                    Category(10,"Новые продукты",""),
                    Category(11,"Новые продукты",""),
                    Category(12,"Новые продукты",""),
                )
                liveData.postValue(categories)
              /*  val res = apiService.getCategories()
                if (res.isSuccessful) {
                    liveStatus.postValue(Status.SUCCESS)
                    liveData.postValue(res.body())
                }*/
            } catch (e: Exception) {
                liveStatus.postValue(Status.ERROR(e.toString()))
            }
        }
    }


}