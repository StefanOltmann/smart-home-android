package de.stefan_oltmann.smarthome.app.ui.devices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import de.stefan_oltmann.smarthome.app.R
import de.stefan_oltmann.smarthome.app.model.DevicePowerState
import de.stefan_oltmann.smarthome.app.model.DeviceType
import de.stefan_oltmann.smarthome.app.ui.components.VerticalDivider
import de.stefan_oltmann.smarthome.app.ui.theme.SmartHomeAppTheme
import de.stefan_oltmann.smarthome.app.ui.theme.black
import de.stefan_oltmann.smarthome.app.ui.theme.white

@Composable
fun PowerStateDeviceCard(
    name: String,
    powerState: MutableState<DevicePowerState>,
    onPowerStateChanged: (powerState: DevicePowerState) -> Unit
) {

    val imageVector: ImageVector = if (powerState.value == DevicePowerState.ON)
        vectorResource(R.drawable.ic_lightbulb_on_outline)
    else
        vectorResource(R.drawable.ic_lightbulb_on_outline_mod)

    DeviceCard(
        name = name,
        statusText = if (powerState.value == DevicePowerState.ON) "Ein" else "Aus",
        imageVector = imageVector
    ) {

        Switch(
            checked = powerState.value == DevicePowerState.ON,
            onCheckedChange = { checked ->

                val newPowerState = if (checked)
                    DevicePowerState.ON
                else
                    DevicePowerState.OFF

                onPowerStateChanged(newPowerState)
            }
        )
    }
}

@Composable
fun PercentageDeviceCard(
    name: String,
    percentage: MutableState<Int>,
    type: DeviceType,
    onPercentageChanged: (percentage: Int) -> Unit
) {

    val imageVector: ImageVector = if (type == DeviceType.ROLLER_SHUTTER) {

        if (percentage.value > 50)
            vectorResource(R.drawable.ic_window_shutter)
        else
            vectorResource(R.drawable.ic_window_shutter_open)

    } else {

        if (percentage.value > 0)
            vectorResource(R.drawable.ic_lightbulb_on_outline)
        else
            vectorResource(R.drawable.ic_lightbulb_on_outline_mod)
    }

    DeviceCard(
        name = name,
        statusText = "${percentage.value}%",
        imageVector = imageVector
    ) {

        Column {

            FloatingActionButton(
                onClick = {
                    onPercentageChanged((percentage.value + 10).coerceAtMost(100))
                },
                modifier = Modifier.preferredSize(24.dp),
                backgroundColor = white
            ) {

                Text(
                    text = "+",
                    color = black,
                    fontSize = TextUnit.Sp(16)
                )
            }

            Spacer(
                modifier = Modifier.padding(4.dp)
            )

            FloatingActionButton(
                onClick = {
                    onPercentageChanged((percentage.value - 10).coerceAtLeast(0))
                },
                modifier = Modifier.preferredSize(24.dp),
                backgroundColor = white
            ) {

                Text(
                    text = "–",
                    color = black,
                    fontSize = TextUnit.Sp(16)
                )
            }
        }
    }
}

@Composable
fun DeviceCard(
    name: String,
    statusText: String,
    imageVector: ImageVector,
    control: @Composable () -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector.copy(
                    defaultWidth = 48.dp,
                    defaultHeight = 48.dp
                ),
                modifier = Modifier
                    .padding(16.dp)
            )

            Column(
                modifier = Modifier
                    .preferredHeight(48.dp)
                    .align(Alignment.CenterVertically)
            ) {

                Text(
                    text = name
                )

                Text(
                    text = statusText,
                    color = Color.LightGray
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            VerticalDivider(
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 16.dp
                ),
                preferredHeight = 48.dp
            )

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .preferredWidth(32.dp)
            ) {

                control()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    SmartHomeAppTheme {
        PowerStateDeviceCard(
            name = "Ventilator",
            powerState = mutableStateOf(DevicePowerState.ON),
            onPowerStateChanged = {}
        )
    }
}