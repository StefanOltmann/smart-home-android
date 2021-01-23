package de.stefan_oltmann.smarthome.app.ui.groups

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GroupListScreen(
    navigateToDeviceListForGroup: (group: String) -> Unit
) {

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column {

            GroupCardRow(
                groups = listOf("Zentral", "Büro", "Wohnzimmer"),
                navigateToDeviceListForGroup
            )

            GroupCardRow(
                groups = listOf("Schlafzimmer", "Alice", "Küche"),
                navigateToDeviceListForGroup
            )
        }
    }
}