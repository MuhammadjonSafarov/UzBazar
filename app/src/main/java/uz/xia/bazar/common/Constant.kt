package uz.xia.bazar.common

import uz.xia.bazar.R

val foodBannerList = listOf(
    R.drawable.banner1, R.drawable.banner2, R.drawable.banner1
)
val marketBannerList = listOf(
    R.drawable.banner1, R.drawable.banner1, R.drawable.banner1
)
const val BASE_URL = "http://192.168.0.101:8082/api/"
const val YANDEX_MAPKIT_API_KEY = "43790952-2b06-4f86-b031-5039a3fd1ea7"
const val MASK_PHONE_NUMBER = "([00])[000]-[00]-[00]"
const val NOMINATION_URL="https://nominatim.openstreetmap.org/"

sealed class Status {
    object SUCCESS : Status()
    object LOADING : Status()
    class ERROR(val text: String) : Status()
}