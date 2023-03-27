package uz.xia.bazar.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.xia.bazar.data.local.entity.UserAddress

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: UserAddress)
    @Query("SELECT * FROM table_address")
    fun getList(): LiveData<List<UserAddress>>
}