package fr.skyle.scanny.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.skyle.scanny.DATASTORE_BASE_NAME
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_BASE_NAME)

class ScannyDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val _dataStore = context.dataStore

    // --- Build version

    suspend fun setCurrentBuildVersion(currentBuildVersion: Long) {
        _dataStore.edit { prefs ->
            prefs[KEY_BUILD_VERSION] = currentBuildVersion
        }
    }

    fun getCurrentBuildVersion(): Long? =
        runBlocking {
            _dataStore.data.map { prefs ->
                prefs[KEY_BUILD_VERSION]
            }.firstOrNull()
        }

    companion object {
        // Build Version
        private val KEY_BUILD_VERSION =
            longPreferencesKey("KEY_BUILD_VERSION")
    }
}