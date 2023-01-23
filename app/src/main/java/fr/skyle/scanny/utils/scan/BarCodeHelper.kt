package fr.skyle.scanny.utils.scan

import androidx.camera.core.ImageAnalysis
import fr.skyle.scanny.enums.ScanModalType
import fr.skyle.scanny.events.scanModalTypeEvent
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.concurrent.Executors

object BarCodeHelper {
    private var barCodeAnalyzer: BarCodeAnalyzer? = null
    private var imageAnalysis: ImageAnalysis? = null

    private val scope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
            Timber.e(exception)
        })

    fun getImageAnalysis(): ImageAnalysis {
        if (imageAnalysis == null) {
            imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            barCodeAnalyzer = BarCodeAnalyzer {
                it.firstOrNull()?.let { barcode ->
                    // Disable scanning when we are showing results
                    isQRCodeScanEnabled(false)

                    scope.launch {
                        // Show success modal
                        scanModalTypeEvent.emit(ScanModalType.ScanSuccessScanModal(barcode))
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