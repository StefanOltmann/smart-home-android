package de.stefan_oltmann.smarthome.app.data.network

/**
 * Represents the Device as data transfer object (DTO)
 */
data class DeviceDto(
    val id: String,
    val name: String,
    val type: String
)
