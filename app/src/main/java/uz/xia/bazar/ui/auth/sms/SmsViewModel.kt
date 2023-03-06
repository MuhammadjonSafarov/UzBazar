package uz.xia.bazar.ui.auth.sms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.xia.bazar.common.Status
import uz.xia.bazar.data.SmsConform
import uz.xia.bazar.network.NetworkManager

private const val TAG = "SmsViewModel"
class SmsViewModel:ViewModel(),ILoginViewModel {
    private val apiService = NetworkManager.getInstaince()
    override val liveStatus = MutableLiveData<Status>()
    override fun onLogin(phoneNumber: String,SmsCode:String) {
        viewModelScope.launch {
            try {
                liveStatus.postValue(Status.LOADING)
                delay(1500)
                val res = apiService.getConformSms(SmsConform(phoneNumber,SmsCode))
                if (res.isSuccessful) {
                    liveStatus.postValue(Status.SUCCESS)
                }else{
                    val errorString=String(res.errorBody()?.bytes()?: byteArrayOf(0))
                    Timber.d("${TAG}: $errorString")
                    liveStatus.postValue(Status.ERROR(errorString))
                }
            } catch (e: Exception) {
                Timber.d("${TAG}: ${e.message}")
                liveStatus.postValue(Status.ERROR(e.message!!))
            }
        }
    }
}
interface ILoginViewModel {
    fun onLogin(phoneNumber: String,SmsCode:String)
    val liveStatus: LiveData<Status>
}
