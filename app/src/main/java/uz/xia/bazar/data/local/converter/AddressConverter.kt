package uz.xia.bazar.data.local.converter

import androidx.room.TypeConverter
import uz.xia.bazar.data.local.entity.AddressType

class AddressConverter {
    @TypeConverter
    fun fromAddressType(value: AddressType): Int{
        return value.v
    }

    @TypeConverter
    fun toAddressType(value: Int): AddressType{
        return when(value){
            1-> AddressType.HOME
            2 -> AddressType.WORK
            else -> AddressType.OTHER
        }
    }
}