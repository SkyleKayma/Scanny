package fr.skyle.scanny.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.skyle.scanny.DATASTORE_BASE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_BASE_NAME)

class SCDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val _dataStore = context.dataStore

    // --- Build version

    suspend fun setCurrentBuildVersion(currentBuildVersion: Int) {
        _dataStore.edit { prefs ->
            prefs[KEY_BUILD_VERSION] = currentBuildVersion
        }
    }

    fun getCurrentBuildVersion(): Int? =
        runBlocking {
            _dataStore.data.map { prefs ->
                prefs[KEY_BUILD_VERSION]
            }.firstOrNull()
        }

    // --- Vibration after scan

    private val isVibrationAfterScanEnabledDefault = true

    suspend fun isVibrationAfterScanEnabled(isEnabled: Boolean) {
        _dataStore.edit { prefs ->
            prefs[KEY_IS_VIBRATION_AFTER_SCAN_ENABLED] = isEnabled
        }
    }

    fun isVibrationAfterScanEnabled(): Boolean =
        runBlocking {
            _dataStore.data.map { prefs ->
                prefs[KEY_IS_VIBRATION_AFTER_SCAN_ENABLED]
            }.firstOrNull() ?: isVibrationAfterScanEnabledDefault
        }

    fun watchIsVibrationAfterScanEnabled(): Flow<Boolean> =
        _dataStore.data.map { prefs ->
            prefs[KEY_IS_VIBRATION_AFTER_SCAN_ENABLED] ?: isVibrationAfterScanEnabledDefault
        }

    // --- Open link after scan

    private val isOpenLinkAfterScanEnabledDefault = false

    suspend fun isOpenLinkAfterScanEnabled(isEnabled: Boolean) {
        _dataStore.edit { prefs ->
            prefs[KEY_OPEN_LINK_AFTER_SCAN] = isEnabled
        }
    }

    fun isOpenLinkAfterScanEnabled(): Boolean =
        runBlocking {
            _dataStore.data.map { prefs ->
                prefs[KEY_OPEN_LINK_AFTER_SCAN]
            }.firstOrNull() ?: isOpenLinkAfterScanEnabledDefault
        }

    fun watchIsOpenLinkAfterScanEnabled(): Flow<Boolean> =
        _dataStore.data.map { prefs ->
            prefs[KEY_OPEN_LINK_AFTER_SCAN] ?: isOpenLinkAfterScanEnabledDefault
        }

    companion object {
        // Generic
        private val KEY_BUILD_VERSION =
            intPreferencesKey("KEY_BUILD_VERSION")

        // Vibration after scan
        private val KEY_IS_VIBRATION_AFTER_SCAN_ENABLED =
            booleanPreferencesKey("KEY_IS_VIBRATION_AFTER_SCAN_ENABLED")

        // Open link after scan
        private val KEY_OPEN_LINK_AFTER_SCAN =
            booleanPreferencesKey("KEY_OPEN_LINK_AFTER_SCAN")
    }
}