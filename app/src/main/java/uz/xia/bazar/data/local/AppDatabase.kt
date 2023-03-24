package uz.xia.bazar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.xia.bazar.data.local.converter.AddressConverter
import uz.xia.bazar.data.local.dao.AddressDao
import uz.xia.bazar.data.local.entity.UserAddress

@Database(
    entities = [UserAddress::class],
    version = 1,
    exportSchema = false)
@TypeConverters(value = [AddressConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context?): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context!!,
                    AppDatabase::class.java, "uzbazar.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}