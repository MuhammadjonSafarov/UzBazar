package uz.xia.bazar.common

import uz.xia.bazar.R

val foodBannerList = listOf(
    R.drawable.banner1,
    R.drawable.banner2,
    R.drawable.banner1
)
val marketBannerList = listOf(
    R.drawable.banner3,
    R.drawable.banner3,
    R.drawable.banner3
)
const val BASE_URL="http://192.168.0.101:8082/api/"

sealed class Status{
    object SUCCESS:Status()
    object LOADING:Status()
    class ERROR(val text:String):Status()
}