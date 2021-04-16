package de.stefan_oltmann.smarthome.app.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import de.stefan_oltmann.smarthome.app.R

/**
 * Password field with toggle for visibility
 */
@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
) {

    val passwordVisible = remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        singleLine = true,
        leadingIcon = { Icon(Icons.Filled.Lock) },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible.value = !passwordVisible.value }
            ) {
                if (passwordVisible.value)
                    Icon(imageVector = vectorResource(R.drawable.ic_visibility_off))
                else
                    Icon(imageVector = vectorResource(R.drawable.ic_visibility))
            }
        }
    )
}
