package uz.xia.bazar.data.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DoublePreference(
    private val pref: SharedPreferences,
    private val key: String,
    private val defValue: String = "0.0"
) : ReadWriteProperty<Any, Double> {
    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double) {
        pref.edit {
            putString(key,value.toString())
        }
    }
    override fun getValue(thisRef: Any, property: KProperty<*>): Double {
        return (pref.getString(key,defValue)?: defValue).toDouble()
    }
}