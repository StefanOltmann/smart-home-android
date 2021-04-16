package de.stefan_oltmann.smarthome.app.data.network

import de.stefan_oltmann.smarthome.app.model.DevicePowerState
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    /**
     * Returns all devices for device discovery.
     */
    @GET("/devices")
    suspend fun findAllDevices(): List<DeviceDto>

    /**
     * Returns all current states of all devices.
     */
    @GET("/devices/current-states")
    suspend fun findAllDeviceStates(): List<DeviceStateDto>

    /**
     * Turns a device (for e.g. a light) on and off.
     */
    @GET("/device/{deviceId}/set/power-state/value/{powerState}")
    suspend fun setDevicePowerState(
        @Path("deviceId") deviceId: String,
        @Path("powerState") powerState: DevicePowerState
    )

    /**
     * Sets a percentage value to a device. For example a dimmer or a roller shutter.
     */
    @GET("/device/{deviceId}/set/percentage/value/{percentage}")
    suspend fun setDevicePercentage(
        @Path("deviceId") deviceId: String,
        @Path("percentage") percentage: Int
    )

    /**
     * Sets a target temperature value to a device. For example a heating.
     */
    @GET("/device/{deviceId}/set/target-temperature/value/{targetTemperature}")
    suspend fun setDeviceTargetTemperature(
        @Path("deviceId") deviceId: String,
        @Path("targetTemperature") targetTemperature: Int
    )

}
