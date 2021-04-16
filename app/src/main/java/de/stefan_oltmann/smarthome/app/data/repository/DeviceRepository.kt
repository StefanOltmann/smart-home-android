package de.stefan_oltmann.smarthome.app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import de.stefan_oltmann.smarthome.app.data.database.DeviceDatabase
import de.stefan_oltmann.smarthome.app.data.database.DeviceEntityMapper
import de.stefan_oltmann.smarthome.app.data.database.DeviceStateEntityMapper
import de.stefan_oltmann.smarthome.app.data.network.DeviceDtoMapper
import de.stefan_oltmann.smarthome.app.data.network.DeviceStateDtoMapper
import de.stefan_oltmann.smarthome.app.data.network.RestApi
import de.stefan_oltmann.smarthome.app.model.Device
import de.stefan_oltmann.smarthome.app.model.DeviceId
import de.stefan_oltmann.smarthome.app.model.DevicePowerState
import de.stefan_oltmann.smarthome.app.model.DeviceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A repository in sense of Domain Driven Design to act as a layer
 * between view models and actual database and network access.
 */
class DeviceRepository(
    private val database: DeviceDatabase
) {

    var restApi: RestApi? = null

    val devices: LiveData<List<Device>> = Transformations.map(database.deviceDao.findAll()) {
        DeviceEntityMapper.toDomainModel(it)
    }

    val deviceStates: LiveData<List<DeviceState>> =
        Transformations.map(database.deviceStateDao.findAll()) {
            DeviceStateEntityMapper.toDomainModel(it)
        }

    /**
     * Update the device power state value.
     */
    suspend fun setDevicePowerState(deviceId: DeviceId, powerState: DevicePowerState) {

        restApi?.let { restApi ->

            withContext(Dispatchers.IO) {

                Log.i(
                    "DeviceRepository",
                    "setDevicePowerState(${deviceId.value}, $powerState) is called"
                )

                restApi.setDevicePowerState(deviceId.value, powerState)
            }
        }
    }

    /**
     * Update the device percentage value.
     */
    suspend fun setDevicePercentage(deviceId: DeviceId, percentage: Int) {

        restApi?.let { restApi ->

            withContext(Dispatchers.IO) {

                Log.i(
                    "DeviceRepository",
                    "setDevicePercentage(${deviceId.value}, $percentage) is called"
                )

                restApi.setDevicePercentage(deviceId.value, percentage)
            }
        }
    }

    /**
     * Refresh the devices stored in the offline cache.
     *
     * It's best practice to always go over a database backed cache like this to
     * ensure that data can be displayed immediately af start of the app.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     */
    suspend fun refreshDevices() {

        restApi?.let { restApi ->

            withContext(Dispatchers.IO) {

                Log.i("DeviceRepository", "refreshDevices() is called")

                /* Retrieve the values from the network. */
                val deviceDtos = restApi.findAllDevices()

                /* Convert it into database entries. */
                val deviceEntities = DeviceDtoMapper.toDatabaseModel(deviceDtos)

                /* Save them to the database. */
                database.deviceDao.insertAll(deviceEntities)
            }
        }
    }

    suspend fun refreshDeviceStates() {

        restApi?.let { restApi ->

            withContext(Dispatchers.IO) {

                Log.i("DeviceRepository", "refreshDeviceStates() is called")

                /* Retrieve the values from the network. */
                val deviceStateDtos = restApi.findAllDeviceStates()

                /* Convert it into database entries. */
                val deviceStateEntities = DeviceStateDtoMapper.toDatabaseModel(deviceStateDtos)

                /* Save them to the database. */
                database.deviceStateDao.insertAll(deviceStateEntities)
            }
        }
    }
}
