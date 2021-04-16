package de.stefan_oltmann.smarthome.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DeviceEntity::class, DeviceStateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DeviceDatabase : RoomDatabase() {

    abstract val deviceDao: DeviceDao

    abstract val deviceStateDao: DeviceStateDao

}

private lateinit var INSTANCE: DeviceDatabase

fun getDatabase(context: Context): DeviceDatabase {

    synchronized(DeviceDatabase::class.java) {

        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DeviceDatabase::class.java,
                "devices"
            ).build()
        }
    }

    return INSTANCE
}
