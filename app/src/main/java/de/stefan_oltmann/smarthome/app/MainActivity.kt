package de.stefan_oltmann.smarthome.app

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import de.stefan_oltmann.smarthome.app.ui.AppViewModel
import de.stefan_oltmann.smarthome.app.ui.SmartHomeApp
import de.stefan_oltmann.smarthome.app.ui.theme.SmartHomeAppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

const val API_URL_SETTINGS_KEY = "apiUrl"
const val AUTH_CODE_SETTINGS_KEY = "authCode"

class MainActivity : AppCompatActivity() {

    private lateinit var dataStore: DataStore<Preferences>

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val appViewModel: AppViewModel by lazy {

        val factory = AppViewModel.Factory(application)

        ViewModelProvider(this, factory).get(AppViewModel::class.java)
    }

    /*
     * FIXME Hack for refreshing values
     */
    private val handler = Handler()
    private var autorefresh = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /*
         * FIXME HACK: Since LiveData-to-State-transformation with
         * observeAsState() is currently broken we do it manually.
         */
        appViewModel.deviceLiveData.observe(this, { devices ->
            appViewModel.devicesMutableState.value = devices
        })

        appViewModel.deviceStatesLiveData.observe(this, {
            appViewModel.onDeviceStatesUpdated()
        })

        dataStore = createDataStore(name = "settings")

        readCredentialsFromDataStore()

        setContent {
            SmartHomeAppTheme {
                SmartHomeApp(
                    appViewModel
                ) { saveCredentialsToDataStore() }
            }
        }
    }

    private fun readCredentialsFromDataStore() = lifecycleScope.launch {

        read(API_URL_SETTINGS_KEY)?.let { apiUrl ->
            appViewModel.apiUrlState.value = apiUrl
        }

        read(AUTH_CODE_SETTINGS_KEY)?.let { authCode ->
            appViewModel.authCodeState.value = authCode
        }
    }

    private fun saveCredentialsToDataStore() = lifecycleScope.launch {

        save(API_URL_SETTINGS_KEY, appViewModel.apiUrlState.value)
        save(AUTH_CODE_SETTINGS_KEY, appViewModel.authCodeState.value)
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override fun onResume() {
        super.onResume()

        autorefresh = true

        doTheAutoRefresh()
    }

    override fun onPause() {
        super.onPause()

        autorefresh = false
    }

    /*
     * TODO Just for testing
     */
    private fun doTheAutoRefresh() {

        handler.postDelayed({

            appViewModel.refreshDeviceStates()

            if (autorefresh)
                doTheAutoRefresh()

        }, 3000)
    }
}