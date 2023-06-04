package fr.skyle.scanny.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.WHILE_SUBSCRIBE_DELAY
import fr.skyle.scanny.data.repository.BarcodeDataRepository
import fr.skyle.scanny.data.vo.BarcodeData
import fr.skyle.scanny.utils.SCDataStore
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    dataStore: SCDataStore,
    private val barcodeDataRepository: BarcodeDataRepository
) : ViewModel() {

    val isVibrationAfterScanEnabled: StateFlow<Boolean> by lazy {
        dataStore.watchIsVibrationAfterScanEnabled()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(WHILE_SUBSCRIBE_DELAY), dataStore.isVibrationAfterScanEnabled())
    }

    val isOpenLinkAfterScanEnabled: StateFlow<Boolean> by lazy {
        dataStore.watchIsOpenLinkAfterScanEnabled()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(WHILE_SUBSCRIBE_DELAY), dataStore.isOpenLinkAfterScanEnabled())
    }

    val isRawContentShown: StateFlow<Boolean> by lazy {
        dataStore.watchIsRawContentShown()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(WHILE_SUBSCRIBE_DELAY), dataStore.isRawContentShown())
    }

    fun insertQRCodeContent(qrCodeContent: QRCodeContent) {
        viewModelScope.launch {
            barcodeDataRepository.insert(
                BarcodeData(
                    type = qrCodeContent.type,
                    format = qrCodeContent.format,
                    scanDate = System.currentTimeMillis(),
                    content = qrCodeContent.rawData
                )
            )
        }
    }
}