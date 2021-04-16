package de.stefan_oltmann.smarthome.app.data.database

import de.stefan_oltmann.smarthome.app.model.DeviceId
import de.stefan_oltmann.smarthome.app.model.DevicePowerState
import de.stefan_oltmann.smarthome.app.model.DeviceState

object DeviceStateEntityMapper {

    fun toDomainModel(deviceStatesEntities: List<DeviceStateEntity>): List<DeviceState> {

        return deviceStatesEntities.map { deviceStateEntity ->
            DeviceState(
                deviceId = DeviceId(deviceStateEntity.deviceId),
                powerState = deviceStateEntity.powerStateOn?.let { powerStateOn ->
                    if (powerStateOn) DevicePowerState.ON else DevicePowerState.OFF
                },
                percentage = deviceStateEntity.percentage,
                currentTemperature = deviceStateEntity.currentTemperature,
                targetTemperature = deviceStateEntity.targetTemperature
            )
        }
    }
}
