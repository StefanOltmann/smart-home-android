package de.stefan_oltmann.smarthome.app.data.network

import de.stefan_oltmann.smarthome.app.model.DevicePowerState

/**
 * Represents the DeviceState as data transfer object (DTO)
 */
class DeviceStateDto(
    val deviceId: String,
    val powerState: DevicePowerState? = null,
    val percentage: Int? = null,
    val currentTemperature: Double? = null,
    val targetTemperature: Double? = null
)
