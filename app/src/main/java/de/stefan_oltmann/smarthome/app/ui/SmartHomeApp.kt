package de.stefan_oltmann.smarthome.app.ui

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import de.stefan_oltmann.smarthome.app.ui.devices.DeviceListScreen
import de.stefan_oltmann.smarthome.app.ui.login.LoginScreen

@Composable
fun SmartHomeApp(
    viewModel: AppViewModel,
    saveCredentialsToDataStore: () -> Unit
) {

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Home App") }
            )
        }
    ) {

        // val navigateToDeviceListForGroup = { _: String -> navController.navigate("deviceList") }

        val navigateToDeviceList = { navController.navigate("deviceList") }

        val onLogin = {

            try {

                /*
                 * Try the provided credentials first
                 */
                viewModel.tryLogin {

                    /*
                     * If credentials are valid and the
                     * server could have been contacted
                     * once, these can be saved.
                     */
                    saveCredentialsToDataStore()

                    navigateToDeviceList()
                }

            } catch (exception: Exception) {

                Toast.makeText(
                    viewModel.getApplication(),
                    "Login failed",
                    Toast.LENGTH_LONG
                ).show()

                exception.printStackTrace()
            }
        }

        NavHost(navController, startDestination = "login") {

            /* Login Screen for entering credentials. */
            composable("login") {
                LoginScreen(
                    apiUrlState = viewModel.apiUrlState,
                    authCodeState = viewModel.authCodeState,
                    onLogin = onLogin
                )
            }

            /* Group Screen for overview of device groups and types. */
//            composable("groupList") {
//                GroupListScreen(
//                        navigateToDeviceListForGroup
//                )
//            }

            /* Device List Screen to control devices. */
            composable("deviceList") {
                DeviceListScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
