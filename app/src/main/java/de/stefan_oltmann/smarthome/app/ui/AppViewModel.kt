package de.stefan_oltmann.smarthome.app.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.stefan_oltmann.smarthome.app.data.database.getDatabase
import de.stefan_oltmann.smarthome.app.data.network.RestApi
import de.stefan_oltmann.smarthome.app.data.network.RestApiClientFactory
import de.stefan_oltmann.smarthome.app.data.repository.DeviceRepository
import de.stefan_oltmann.smarthome.app.model.Device
import de.stefan_oltmann.smarthome.app.model.DeviceId
import de.stefan_oltmann.smarthome.app.model.DevicePowerState
import kotlinx.coroutines.launch
import kotlin.collections.set

/*
 * TODO Currently there is only one view model for everything since the app it not that complex right now.
 */
class AppViewModel(application: Application) : AndroidViewModel(application) {

    val apiUrlState = mutableStateOf("")
    val authCodeState = mutableStateOf("")

    private val deviceRepository = DeviceRepository(
        getDatabase(application)
    )

    val deviceLiveData = deviceRepository.devices
    val deviceStatesLiveData = deviceRepository.deviceStates

    /*
     * FIXME HACK: Transformation over the MainActivity because of a bug
     */
    val devicesMutableState = mutableStateOf<List<Device>>(emptyList())

    private val devicePowerStateMap = mutableMapOf<DeviceId, MutableState<DevicePowerState>>()
    private val devicePercentageMap = mutableMapOf<DeviceId, MutableState<Int>>()

    /**
     * FIXME Should be private.
     *
     * Due to a bug it needs to be called from outside.
     */
    fun onDeviceStatesUpdated() {

        deviceStatesLiveData.value?.let { deviceStates ->

            for (deviceState in deviceStates) {

                deviceState.powerState?.let { powerState ->
                    getOrCreateDevicePowerState(deviceState.deviceId).value = powerState
                }

                deviceState.percentage?.let { percentage ->
                    getOrCreateDevicePercentage(deviceState.deviceId).value = percentage
                }
            }
        }
    }

    private fun getOrCreateDevicePowerState(deviceId: DeviceId): MutableState<DevicePowerState> {

        var devicePowerState = devicePowerStateMap[deviceId]

        if (devicePowerState == null) {
            devicePowerState = mutableStateOf(DevicePowerState.OFF)
            devicePowerStateMap[deviceId] = devicePowerState
        }

        return devicePowerState
    }

    private fun getOrCreateDevicePercentage(deviceId: DeviceId): MutableState<Int> {

        var devicePercentage = devicePercentageMap[deviceId]

        if (devicePercentage == null) {
            devicePercentage = mutableStateOf(0)
            devicePercentageMap[deviceId] = devicePercentage
        }

        return devicePercentage
    }

    fun getDevicePowerState(deviceId: DeviceId): MutableState<DevicePowerState> {
        return getOrCreateDevicePowerState(deviceId)
    }

    fun getDevicePercentage(deviceId: DeviceId): MutableState<Int> {
        return getOrCreateDevicePercentage(deviceId)
    }

    fun onDevicePowerStateChanged(deviceId: DeviceId, powerState: DevicePowerState) {

        /* Update local value. */
        getOrCreateDevicePowerState(deviceId).value = powerState

        viewModelScope.launch {

            try {

                /* Send to network. */
                deviceRepository.setDevicePowerState(deviceId, powerState)

            } catch (exception: Exception) {

                Toast.makeText(
                    getApplication(),
                    "Failed",
                    Toast.LENGTH_LONG
                ).show()

                exception.printStackTrace()
            }
        }
    }

    fun onDevicePercentageChanged(deviceId: DeviceId, percentage: Int) {

        /* Update local value. */
        getOrCreateDevicePercentage(deviceId).value = percentage

        viewModelScope.launch {

            try {

                /* Send to network. */
                deviceRepository.setDevicePercentage(deviceId, percentage)

            } catch (exception: Exception) {

                Toast.makeText(
                    getApplication(),
                    "Failed",
                    Toast.LENGTH_LONG
                ).show()

                exception.printStackTrace()
            }
        }
    }

    /**
     * Tries to login to the service using the provided credentials
     */
    fun tryLogin(
        onSuccess: () -> Unit
    ) {

        deviceRepository.restApi = createRestApi()

        refreshDevices() {

            onSuccess()

            refreshDeviceStates()
        }
    }

    private fun createRestApi(): RestApi {

        // TODO FIXME Hardcoded credentials!
        return RestApiClientFactory.createRestApiClient(
            baseUrl = apiUrlState.value,
            authCode = authCodeState.value
        )
    }

    /**
     * Refresh data from the repository.
     *
     * Use a coroutine launch to run in a background thread.
     */
    private fun refreshDevices(
        onSuccess: () -> Unit,
    ) {

        viewModelScope.launch {

            try {

                /* Refresh device states. */
                deviceRepository.refreshDevices()

                onSuccess()

            } catch (exception: Exception) {

                Toast.makeText(
                    getApplication(),
                    "Failed to update device list.",
                    Toast.LENGTH_SHORT
                ).show()

                exception.printStackTrace()
            }
        }
    }

    fun refreshDeviceStates() {

        viewModelScope.launch {

            try {

                /* Refresh device states. */
                deviceRepository.refreshDeviceStates()

            } catch (exception: Exception) {

                Toast.makeText(
                    getApplication(),
                    "Failed to update device states.",
                    Toast.LENGTH_SHORT
                ).show()

                exception.printStackTrace()
            }
        }
    }

    /**
     * Factory for constructing DevicesViewModel with parameter
     */
    class Factory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppViewModel(application) as T
            }

            throw IllegalArgumentException("Unable to construct DevicesViewModel")
        }
    }
}