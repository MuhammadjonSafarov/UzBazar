package uz.xia.bazar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "table_address")
data class UserAddress(
    val name:String,
    val street:String,
    val longitude:Double,
    val latitude:Double,
    val time:Long,
    val type:AddressType,
    @PrimaryKey(autoGenerate = true)
    val id:Long=0
)
enum class AddressType(val v:Int){
    HOME(1),
    WORK(2),
    OTHER(3)
}