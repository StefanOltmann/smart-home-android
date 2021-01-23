package de.stefan_oltmann.smarthome.app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the Device as database entity
 */
@Entity(tableName = "devices_current_states")
data class DeviceStateEntity(

    @PrimaryKey
    @ColumnInfo(name = "device_id")
    val deviceId: String,

    @ColumnInfo(name = "power_state_on")
    val powerStateOn: Boolean?,

    @ColumnInfo(name = "percentage")
    val percentage: Int?,

    @ColumnInfo(name = "current_temperature")
    val currentTemperature: Double? = null,

    @ColumnInfo(name = "target_temperature")
    val targetTemperature: Double? = null

)