package de.stefan_oltmann.smarthome.app.data.database

import de.stefan_oltmann.smarthome.app.model.Device
import de.stefan_oltmann.smarthome.app.model.DeviceId
import de.stefan_oltmann.smarthome.app.model.DeviceType

object DeviceEntityMapper {

    fun toDomainModel(deviceEntities: List<DeviceEntity>): List<Device> {

        return deviceEntities.map { deviceEntity ->
            Device(
                id = DeviceId(deviceEntity.id),
                name = deviceEntity.name,
                type = DeviceType.valueOf(deviceEntity.type)
            )
        }
    }
}