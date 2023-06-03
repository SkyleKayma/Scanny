package fr.skyle.scanny.ui.scan.components.camera

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.utils.BarCodeAnalyzer
import java.util.concurrent.Executors

@Composable
fun CameraView(
    onBarcodeDetected: (Barcode) -> Unit,
    onDetectQRCodeChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // Context
    val context = LocalContext.current

    // Lifecycle
    val lifecycleOwner = LocalLifecycleOwner.current

    // Remember
    var camera by remember { mutableStateOf<Camera?>(null) }
    val cameraProviderFuture by remember {
        mutableStateOf(ProcessCameraProvider.getInstance(context))
    }
    var isCameraShown by remember {
        mutableStateOf(true)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE,
                Lifecycle.Event.ON_START,
                Lifecycle.Event.ON_RESUME -> {
                    isCameraShown = true
                }

                Lifecycle.Event.ON_PAUSE,
                Lifecycle.Event.ON_STOP -> {
                    isCameraShown = false
                }

                Lifecycle.Event.ON_DESTROY -> {}
                Lifecycle.Event.ON_ANY -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (isCameraShown) {
        AndroidView(
            modifier = modifier,
            factory = { viewContext ->
                PreviewView(viewContext).also { previewView ->
                    cameraProviderFuture.apply {
                        addListener(
                            {
                                val preview = androidx.camera.core.Preview.Builder().build().also {
                                    it.setSurfaceProvider(previewView.surfaceProvider)
                                }

                                val imageAnalysis = ImageAnalysis.Builder()
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build()

                                imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), BarCodeAnalyzer {
                                    it.firstOrNull()?.let { barcode ->
                                        onBarcodeDetected(barcode)
                                    }
                                })

                                // Select rear camera
                                val cameraSelector = CameraSelector.Builder()
                                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                    .build()

                                try {
                                    val cameraProvider = get()

                                    cameraProvider.unbindAll()
                                    onDetectQRCodeChanged(true)

                                    camera = cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        cameraSelector,
                                        imageAnalysis,
                                        preview
                                    )
                                } catch (e: Exception) {
                                    // Already bind
                                }
                            },
                            ContextCompat.getMainExecutor(context)
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewCameraView() {
    SCTheme {
        CameraView(
            onBarcodeDetected = {},
            onDetectQRCodeChanged = {}
        )
    }
}