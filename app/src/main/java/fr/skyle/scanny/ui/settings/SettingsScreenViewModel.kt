package fr.skyle.scanny.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.utils.SCDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val dataStore: SCDataStore
) : ViewModel() {

    val isVibrationAfterScanEnabled: StateFlow<Boolean> by lazy {
        dataStore.watchIsVibrationAfterScanEnabled()
            .stateIn(viewModelScope, SharingStarted.Lazily, dataStore.isVibrationAfterScanEnabled())
    }

    val isOpenLinkAfterScanEnabled: StateFlow<Boolean> by lazy {
        dataStore.watchIsOpenLinkAfterScanEnabled()
            .stateIn(viewModelScope, SharingStarted.Lazily, dataStore.isOpenLinkAfterScanEnabled())
    }

    fun isVibrationAfterScanEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.isVibrationAfterScanEnabled(isEnabled)
        }
    }

    fun isOpenLinkAfterScanEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.isOpenLinkAfterScanEnabled(isEnabled)
        }
    }
}
