package de.stefan_oltmann.smarthome.app.model

/**
 * Class representing the power state and percentage.
 */
data class DeviceState(
    val deviceId: DeviceId,
    val powerState: DevicePowerState? = null,
    val percentage: Int? = null,
    val currentTemperature: Double? = null,
    val targetTemperature: Double? = null
)
