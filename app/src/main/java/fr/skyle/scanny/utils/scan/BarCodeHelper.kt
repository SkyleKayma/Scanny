package fr.skyle.scanny.utils.scan

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import fr.skyle.scanny.enums.ScanModalType
import fr.skyle.scanny.events.scanModalTypeEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executors

object BarCodeHelper {
    private var barCodeAnalyzer: BarCodeAnalyzer? = null
    private var imageAnalysis: ImageAnalysis? = null

    private val scope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
            Timber.e(exception)
        })

    init {
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
    }

    fun getImageAnalysis(): ImageAnalysis {
        if (imageAnalysis == null) {
            imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis?.setAnalyzer(Executors.newSingleThreadExecutor(), barCodeAnalyzer!!)
        }
        return imageAnalysis!!
    }

    fun isQRCodeScanEnabled(isEnabled: Boolean) {
        barCodeAnalyzer?.canDetectCode = isEnabled
    }
}