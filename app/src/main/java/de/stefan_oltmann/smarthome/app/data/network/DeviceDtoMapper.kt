package de.stefan_oltmann.smarthome.app.data.network

import de.stefan_oltmann.smarthome.app.data.database.DeviceEntity

object DeviceDtoMapper {

    fun toDatabaseModel(deviceDtos: List<DeviceDto>): List<DeviceEntity> {

        return deviceDtos.map { deviceDto ->
            DeviceEntity(
                id = deviceDto.id,
                name = deviceDto.name,
                type = deviceDto.type
            )
        }
    }
}
