package de.stefan_oltmann.smarthome.app.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeviceStateDao {

    @Query("SELECT * FROM devices_current_states")
    fun findAll(): LiveData<List<DeviceStateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(deviceStates: List<DeviceStateEntity>)

}