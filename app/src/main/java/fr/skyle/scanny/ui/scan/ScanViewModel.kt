package fr.skyle.scanny.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.utils.SCDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    dataStore: SCDataStore
) : ViewModel() {

    val isVibrationAfterScanEnabled: StateFlow<Boolean> by lazy {
        dataStore.watchIsVibrationAfterScanEnabled()
            .stateIn(viewModelScope, SharingStarted.Lazily, dataStore.isVibrationAfterScanEnabled())
    }

    val isOpenLinkAfterScanEnabled: StateFlow<Boolean> by lazy {
        dataStore.watchIsOpenLinkAfterScanEnabled()
            .stateIn(viewModelScope, SharingStarted.Lazily, dataStore.isOpenLinkAfterScanEnabled())
    }

    val isRawContentShown: StateFlow<Boolean> by lazy {
        dataStore.watchIsRawContentShown()
            .stateIn(viewModelScope, SharingStarted.Lazily, dataStore.isRawContentShown())
    }
}