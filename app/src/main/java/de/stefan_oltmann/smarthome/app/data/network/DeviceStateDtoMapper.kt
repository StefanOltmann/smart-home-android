package de.stefan_oltmann.smarthome.app.data.network

import de.stefan_oltmann.smarthome.app.data.database.DeviceStateEntity
import de.stefan_oltmann.smarthome.app.model.DevicePowerState

object DeviceStateDtoMapper {

    fun toDatabaseModel(deviceStateDtos: List<DeviceStateDto>): List<DeviceStateEntity> {

        return deviceStateDtos.map { deviceStateDto ->
            DeviceStateEntity(
                deviceId = deviceStateDto.deviceId,
                powerStateOn = deviceStateDto.powerState == DevicePowerState.ON,
                percentage = deviceStateDto.percentage,
                currentTemperature = deviceStateDto.currentTemperature,
                targetTemperature = deviceStateDto.targetTemperature
            )
        }
    }
}