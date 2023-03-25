package uz.xia.bazar.data.pref

import android.content.SharedPreferences
private const val KEY_CURRENT_LONGITUDE="KEY_CURRENT_LONGITUDE"
private const val KEY_CURRENT_LATITUDE="KEY_CURRENT_LATITUDE"
private const val KEY_ADDRESS_NAME="KEY_ADDRESS_NAME"
class PreferenceManager(pref:SharedPreferences):IPreferenceManager {
    override var longitude: Double by DoublePreference(pref, KEY_CURRENT_LONGITUDE)
    override var latitude: Double by DoublePreference(pref, KEY_CURRENT_LATITUDE)
    override var addressName: String by StringPreference(pref,KEY_ADDRESS_NAME)
}