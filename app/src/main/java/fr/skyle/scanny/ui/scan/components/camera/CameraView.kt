package fr.skyle.scanny.ui.scan.components.camera

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.utils.scan.BarCodeHelper

@Composable
fun CameraView(
    isFlashEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    // Lifecycle
    val lifecycleOwner = LocalLifecycleOwner.current

    // Remember
    var camera by remember { mutableStateOf<Camera?>(null) }
    val isFlashOn by remember(camera, isFlashEnabled) {
        mutableStateOf(
            if (camera?.cameraInfo?.hasFlashUnit() == true) {
                isFlashEnabled
            } else false
        )
    }

    LaunchedEffect(key1 = isFlashOn, block = {
        camera?.cameraControl?.enableTorch(isFlashOn)
    })

    AndroidView(
        modifier = modifier,
        factory = { context ->
            PreviewView(context).also { previewView ->
                ProcessCameraProvider.getInstance(context).apply {
                    addListener(
                        {
                            val cameraProvider = get()
                            val preview = androidx.camera.core.Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            // Select rear or front camera
                            val cameraSelector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()

                            cameraProvider.unbindAll()
                            camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                BarCodeHelper.getImageAnalysis(),
                                preview
                            )
                        },
                        ContextCompat.getMainExecutor(context)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewCameraView() {
    SCTheme {
        CameraView(
            isFlashEnabled = false
        )
    }
}