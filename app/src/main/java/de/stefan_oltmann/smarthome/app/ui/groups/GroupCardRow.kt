package de.stefan_oltmann.smarthome.app.ui.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GroupCardRow(
    groups: List<String>,
    navigateToDeviceListForGroup: (group: String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        for (group in groups) {

            Box(
                modifier = Modifier.weight(1f)
            ) {

                GroupCard(
                    group,
                    Icons.Default.Home,
                    navigateToDeviceListForGroup
                )
            }
        }
    }
}