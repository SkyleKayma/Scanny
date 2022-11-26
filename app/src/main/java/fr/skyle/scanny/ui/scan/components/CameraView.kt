package fr.skyle.scanny.ui.scan.components

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import fr.skyle.scanny.R

@Composable
fun CameraView(
    imageAnalysis: ImageAnalysis,
    hasFlash: Boolean
) {
    // Lifecycle
    val lifecycleOwner = LocalLifecycleOwner.current

    // Remember
    var camera by remember { mutableStateOf<Camera?>(null) }

    LaunchedEffect(key1 = camera, block = {
        if (camera?.cameraInfo?.hasFlashUnit() == true) {
            camera?.cameraControl?.enableTorch(hasFlash)
        }
    })

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context)
                val executor = ContextCompat.getMainExecutor(context)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

                cameraProviderFuture.addListener(
                    {
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
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
                            imageAnalysis,
                            preview
                        )
                    },
                    executor
                )
                previewView
            }
        )
    }
}

@Composable
fun CameraFilter() {
    val backgroundColor = colorResource(id = R.color.sc_black).copy(alpha = 0.5f)

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner,
            topRow, bottomRow, leftColumn, rightColumn, centerSquare) = createRefs()

        // Center square
        Spacer(
            modifier = Modifier
                .size(250.dp)
                .constrainAs(centerSquare) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        // Borders
        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(topRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(centerSquare.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(bottomRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(centerSquare.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(leftColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(centerSquare.start)
                    top.linkTo(topRow.bottom)
                    bottom.linkTo(bottomRow.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(rightColumn) {
                    start.linkTo(centerSquare.end)
                    end.linkTo(parent.end)
                    top.linkTo(topRow.bottom)
                    bottom.linkTo(bottomRow.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Image(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(topLeftCorner) {
                    top.linkTo(topRow.bottom, (-4).dp)
                    start.linkTo(leftColumn.end, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_top_left),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_background_icon))
        )

        Image(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(topRightCorner) {
                    top.linkTo(topRow.bottom, (-4).dp)
                    end.linkTo(rightColumn.start, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_top_right),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_background_icon))
        )

        Image(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(bottomLeftCorner) {
                    bottom.linkTo(bottomRow.top, (-4).dp)
                    start.linkTo(leftColumn.end, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_bottom_left),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_background_icon))
        )

        Image(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(bottomRightCorner) {
                    bottom.linkTo(bottomRow.top, (-4).dp)
                    end.linkTo(rightColumn.start, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_bottom_right),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_background_icon))
        )
    }
}