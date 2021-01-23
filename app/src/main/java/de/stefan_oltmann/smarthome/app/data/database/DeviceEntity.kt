package de.stefan_oltmann.smarthome.app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the Device as database entity
 */
@Entity(tableName = "devices")
data class DeviceEntity(
        @PrimaryKey
        val id: String,
        val name: String,
        val type: String
)