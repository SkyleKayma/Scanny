package fr.skyle.scanny.utils

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

class BarCodeAnalyzer(
    private val onBarCodeDetected: (qrCodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {

    // Options
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    // Scanner
    private val scanner = BarcodeScanning.getClient(options)

    // To control it when a barcode has been detected and we are showing results
    private var canDetectCode: Boolean = true

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        try {
            imageProxy.image?.let { image ->
                // Rotate image
                val visionImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)

                // Process
                scanner.process(visionImage)
                    .addOnSuccessListener { barcodes ->
                        if (canDetectCode && barcodes.isNotEmpty()) {
                            onBarCodeDetected(barcodes)
                        }
                        imageProxy.close()
                    }.addOnFailureListener {
                        Timber.e(it)
                        imageProxy.close()
                    }
            }
        } catch (error: Exception) {
            Timber.e(error)
        }
    }
}