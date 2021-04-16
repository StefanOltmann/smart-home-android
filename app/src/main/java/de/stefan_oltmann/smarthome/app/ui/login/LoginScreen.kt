package de.stefan_oltmann.smarthome.app.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import de.stefan_oltmann.smarthome.app.R
import de.stefan_oltmann.smarthome.app.ui.components.PasswordField

@Composable
fun LoginScreen(
    apiUrlState: MutableState<String>,
    authCodeState: MutableState<String>,
    onLogin: () -> Unit
) {

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                vectorResource(R.drawable.ic_smart_home_logo).copy(
                    defaultWidth = 200.dp,
                    defaultHeight = 200.dp
                ),
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))

            TextField(
                value = apiUrlState.value,
                onValueChange = { apiUrlState.value = it },
                label = { Text(text = "API URL") },
                leadingIcon = { Icon(vectorResource(R.drawable.ic_api)) },
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(16.dp))

            PasswordField(
                value = authCodeState.value,
                onValueChange = { authCodeState.value = it },
                label = { Text(text = "Auth Code") },
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = { onLogin() }
            ) {
                Text("Login")
            }
        }
    }
}