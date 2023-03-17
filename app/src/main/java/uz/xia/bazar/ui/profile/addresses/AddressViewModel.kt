package uz.xia.bazar.ui.profile.addresses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uz.xia.bazar.data.local.AppDatabase
import uz.xia.bazar.data.local.entity.Address

interface IAddressViewModel{
    fun loadAddress():LiveData<List<Address>>
}
class AddressViewModel(app:Application):AndroidViewModel(app),IAddressViewModel{

    private val addressDao=AppDatabase.getInstance(app).addressDao()
    override fun loadAddress(): LiveData<List<Address>> = addressDao.getList()

}