package uz.xia.bazar.ui.profile.addresses.add

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.util.SharedPreferencesUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.xia.bazar.data.local.AppDatabase
import uz.xia.bazar.data.local.entity.AddressType
import uz.xia.bazar.data.local.entity.UserAddress
import uz.xia.bazar.data.pref.IPreferenceManager
import uz.xia.bazar.data.pref.PreferenceManager
import uz.xia.bazar.network.NominationService
import uz.xia.bazar.ui.profile.addresses.add.model.NearEmpty
import uz.xia.bazar.ui.profile.addresses.add.model.NearLoading
import uz.xia.bazar.ui.profile.addresses.add.model.NearbyPlace
import uz.xia.bazar.ui.profile.addresses.add.model.PlaceMapper
import java.util.*

interface IAddAddressViewModel {
    val livePlaceList: LiveData<List<NearbyPlace>>
    fun searchPlace(query: String, lang: String)
    fun loadNearbyPlaces()

    fun geoCode(latitude: Double, longitude: Double, lang: String)
    fun saveAddress(addressName: String)
}

private const val TAG = "AddAddressViewModel"

class AddAddressViewModel(app: Application) : AndroidViewModel(app), IAddAddressViewModel {
    override val livePlaceList = MutableLiveData<List<NearbyPlace>>()
    private val nominationService = NominationService.getInstance()
    private val addressDao = AppDatabase.getInstance(app).addressDao()
    private val preference: IPreferenceManager =
        PreferenceManager(androidx.preference.PreferenceManager.getDefaultSharedPreferences(app))
    private var lastSearchJob: Job? = null
    private val placeToNearMapper = PlaceMapper(preference.latitude, preference.longitude)

    override fun loadNearbyPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                livePlaceList.postValue(listOf(NearLoading()))
                val response = nominationService.searchPlaces("uzbekistan", "uz")
                if (response.isNotEmpty()) {
                    val listNearbyPlace = ArrayList<NearbyPlace>()
                    for (place in response) {
                        place.latitude = place.lat.toDouble()
                        place.longitude = place.lon.toDouble()

                        val lastIndexComma = place.title.lastIndexOf(',')
                        place.title = if (lastIndexComma == -1)
                            place.title
                        else
                            place.title.substring(0, lastIndexComma)
                        place.title = place.title.replace(Regex(", \\d{6,}"), "")

                        listNearbyPlace.add(placeToNearMapper.transform(place))
                    }
                    livePlaceList.postValue(listNearbyPlace)
                } else
                    livePlaceList.postValue(listOf(NearEmpty()))
            } catch (e: Exception) {
                Timber.d(e)
                livePlaceList.postValue(listOf(NearEmpty()))
            }
        }
    }

    override fun searchPlace(query: String, lang: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                livePlaceList.postValue(listOf(NearLoading()))
                delay(500)
                val response = nominationService.searchPlaces("$query,uzbekistan", lang)
                if (response.isNotEmpty()) {
                    val listNearbyPlace = ArrayList<NearbyPlace>()
                    for (place in response) {
                        place.latitude = place.lat.toDouble()
                        place.longitude = place.lon.toDouble()

                        val lastIndexComma = place.title.lastIndexOf(',')
                        place.title =
                            if (lastIndexComma == -1) place.title else place.title.substring(
                                0,
                                lastIndexComma
                            )
                        place.title = place.title.replace(Regex(", \\d{6,}"), "")

                        Location.distanceBetween(
                            41.287235, 69.218949,
                            place.latitude!!,
                            place.longitude!!,
                            place.distance
                        )
                        listNearbyPlace.add(
                            NearbyPlace(
                                place.placeId,
                                place.title,
                                place.title,
                                place.distance[0],
                                place.latitude!!,
                                place.longitude!!
                            )
                        )
                    }
                    listNearbyPlace.sortBy { it.distance }
                    livePlaceList.postValue(listNearbyPlace)
                } else
                    livePlaceList.postValue(listOf(NearEmpty()))
            } catch (e: Exception) {
                Timber.d(e)
                livePlaceList.postValue(listOf(NearEmpty()))
            }
        }
    }

    override fun geoCode(latitude: Double, longitude: Double, lang: String) {
        lastSearchJob?.cancel()
        lastSearchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                livePlaceList.postValue(listOf(NearLoading()))
                val response = nominationService.loadAddress(latitude, longitude, lang)
                var title = response?.displayName ?: throw Exception("Empty place name")
                val lastIndexComma = title.lastIndexOf(',')
                title =
                    if (lastIndexComma == -1) title else title.substring(
                        0,
                        lastIndexComma
                    )
                title = title.replace(Regex(", \\d{6,}"), "")
                preference.addressName = title
                val distance = FloatArray(1)
                Location.distanceBetween(
                    preference.latitude,
                    preference.longitude,
                    latitude,
                    longitude,
                    distance
                )
                livePlaceList.postValue(
                    listOf(
                        NearbyPlace(
                            Date().time,
                            title,
                            title,
                            distance[0],
                            latitude,
                            longitude
                        )
                    )
                )
            } catch (e: Exception) {
                Timber.d(e)
                livePlaceList.postValue(listOf(NearEmpty()))
            }
        }
    }

    override fun saveAddress(addressName: String) {
        viewModelScope.launch {
            val time = Date().time
            val userAddress = UserAddress(
                name = addressName,
                41.287235,
                69.218949,
                time,
                AddressType.HOME
            )
            addressDao.insertAddress(userAddress)
        }
    }


}