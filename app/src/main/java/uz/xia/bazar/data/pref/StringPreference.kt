package uz.xia.bazar.data.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringPreference(
    private val pref: SharedPreferences,
    private val key: String,
    private val defValue: String = ""
) : ReadWriteProperty<Any, String> {
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        pref.edit {
            putString(key, value)
        }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return pref.getString(key, defValue) ?: defValue
    }
}