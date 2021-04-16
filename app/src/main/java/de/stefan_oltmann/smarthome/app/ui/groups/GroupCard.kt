package de.stefan_oltmann.smarthome.app.ui.groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GroupCard(
    name: String,
    image: ImageVector,
    navigateToDeviceListForGroup: (group: String) -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { navigateToDeviceListForGroup(name) })
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                image.copy(
                    defaultWidth = 48.dp,
                    defaultHeight = 48.dp
                )
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                modifier = Modifier.preferredHeight(40.dp)
            )
        }
    }
}
