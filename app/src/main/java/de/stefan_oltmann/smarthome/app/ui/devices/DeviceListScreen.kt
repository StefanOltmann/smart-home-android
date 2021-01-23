package de.stefan_oltmann.smarthome.app.ui.devices

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.stefan_oltmann.smarthome.app.model.DeviceType
import de.stefan_oltmann.smarthome.app.ui.AppViewModel

@Composable
fun DeviceListScreen(
    viewModel: AppViewModel
) {

    // CRASHES. Google "Landroidx/compose/runtime/SlotTable$Companion;"
    // val devicesViewModel by viewModel.devices.observeAsState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        val devices = viewModel.devicesMutableState.value

        Column {

            LazyColumn {

                items(devices) { device ->

                    if (device.type == DeviceType.LIGHT_SWITCH) {

                        PowerStateDeviceCard(
                            name = device.name,
                            powerState = viewModel.getDevicePowerState(device.id),
                            onPowerStateChanged = { powerState ->
                                viewModel.onDevicePowerStateChanged(device.id, powerState)
                            }
                        )
                    }

                    if (device.type == DeviceType.DIMMER
                        || device.type == DeviceType.ROLLER_SHUTTER
                    ) {

                        PercentageDeviceCard(
                            name = device.name,
                            percentage = viewModel.getDevicePercentage(device.id),
                            type = device.type,
                            onPercentageChanged = { percentage ->
                                viewModel.onDevicePercentageChanged(device.id, percentage)
                            }
                        )
                    }
                }
            }
        }
    }
}