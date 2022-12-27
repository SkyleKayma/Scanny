package fr.skyle.scanny.ui.home

import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.enums.ModalType
import fr.skyle.scanny.events.modalTypeEvent
import fr.skyle.scanny.utils.scan.BarCodeAnalyzer
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private var barCodeAnalyzer: BarCodeAnalyzer? = null
    private var imageAnalysis: ImageAnalysis? = null

    fun getImageAnalysis(): ImageAnalysis {
        if (imageAnalysis == null) {
            imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            barCodeAnalyzer = BarCodeAnalyzer {
                it.firstOrNull()?.let { barcode ->
                    // Disable scanning when we are showing results
                    isQRCodeScanEnabled(false)

                    viewModelScope.launch {
                        // Show success modal
                        modalTypeEvent.emit(ModalType.ScanSuccessModal(barcode))
                    }
                }
            }

            imageAnalysis?.setAnalyzer(Executors.newSingleThreadExecutor(), barCodeAnalyzer!!)
        }
        return imageAnalysis!!
    }

    fun isQRCodeScanEnabled(isEnabled: Boolean) {
        barCodeAnalyzer?.canDetectCode = isEnabled
    }
}