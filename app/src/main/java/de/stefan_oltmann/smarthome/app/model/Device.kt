package de.stefan_oltmann.smarthome.app.model

/**
 * Class representing a device like a light switch, a dimmer or a roller shutter.
 */
data class Device(
    val id: DeviceId,
    val name: String,
    val type: DeviceType
)
