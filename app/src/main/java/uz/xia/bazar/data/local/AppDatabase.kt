package uz.xia.bazar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.xia.bazar.data.local.dao.AddressDao
import uz.xia.bazar.data.local.entity.Address

@Database(
    entities = [Address::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao():AddressDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context?): AppDatabase{
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context!!,
                    AppDatabase::class.java, "uzbazar.db")
                        .build()
            }
            return INSTANCE!!
        }
    }
}