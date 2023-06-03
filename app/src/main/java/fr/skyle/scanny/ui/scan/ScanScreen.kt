package fr.skyle.scanny.ui.scan

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.ModalType
import fr.skyle.scanny.events.modalTypeEvent
import fr.skyle.scanny.ext.navigateToLink
import fr.skyle.scanny.ext.toQRCodeContent
import fr.skyle.scanny.ext.vibrateScan
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.scan.components.ScanScreenContent
import fr.skyle.scanny.ui.scanDetail.ScanDetail
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    navigateToSettings: () -> Unit,
    onAddToContact: (QRCodeContent.ContactContent) -> Unit,
    viewModel: ScanViewModel = hiltViewModel()
) {
    // Context
    val context = LocalContext.current

    // Clip Manager
    val clipboardManager = LocalClipboardManager.current

    // Flow
    val isVibrationAfterScanEnabled by viewModel.isVibrationAfterScanEnabled.collectAsStateWithLifecycle()
    val isOpenLinkAfterScanEnabled by viewModel.isOpenLinkAfterScanEnabled.collectAsStateWithLifecycle()
    val isRawContentShown by viewModel.isRawContentShown.collectAsStateWithLifecycle()


    // Remember
    val scope = rememberCoroutineScope()
    var canDetectQRCode by remember {
        mutableStateOf(true)
    }

    var modalContent: @Composable () -> Unit by remember {
        mutableStateOf({
            Box(modifier = Modifier.fillMaxSize())
        })
    }
    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                canDetectQRCode = true
            }

            true
        }
    )

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var isFlashEnabled by remember { mutableStateOf(false) }

    val galleryLauncher: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            try {
                BarcodeScanning.getClient()
                    .process(InputImage.fromFilePath(context, uri!!))
                    .addOnSuccessListener {
                        it.firstOrNull()?.let { barcode ->
                            scope.launch {
                                // Show success modal
                                modalTypeEvent.emit(ModalType.ScanSuccessModal(barcode))
                            }
                        } ?: Toast.makeText(context, context.getString(R.string.scan_no_qr_code_found), Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Timber.e(it)
                    }
            } catch (e: Exception) {
                Timber.e(e)
                Toast.makeText(context, context.getString(R.string.scan_file_not_found), Toast.LENGTH_SHORT).show()
            }
        }

    val onDismissBottomSheet: () -> Unit = {
        scope.launch {
            modalState.hide()
            canDetectQRCode = true
        }
    }

    // Permission
    LaunchedEffect(key1 = Unit, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    // Back Handler
    BackHandler(enabled = modalState.isVisible || modalState.targetValue == ModalBottomSheetValue.Expanded) {
        onDismissBottomSheet()
    }

    // Handle modal
    LaunchedEffect(modalState) {
        modalTypeEvent.collectLatest { bottomSheetType ->
            when (bottomSheetType) {
                is ModalType.ScanSuccessModal -> {
                    val qrCodeContent = bottomSheetType.barcode.toQRCodeContent

                    if (qrCodeContent is QRCodeContent.UrlContent && isOpenLinkAfterScanEnabled) {
                        context.navigateToLink(qrCodeContent.url)

                        // Wait before enabling scan feature, otherwise it will be called multiple times before browser open
                        delay(2_000L)
                    } else {
                        modalContent = {
                            ScanDetail(
                                barcode = bottomSheetType.barcode,
                                onCopyContent = {
                                    clipboardManager.setText(it)
                                },
                                onAddToContact = onAddToContact,
                                isRawContentShown = { isRawContentShown }
                            )
                        }
                        modalState.show()
                    }
                }

                null -> onDismissBottomSheet()
            }
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(SCAppTheme.colors.backgroundBlack),
        sheetBackgroundColor = SCAppTheme.colors.transparent,
        sheetElevation = 0.dp,
        sheetState = modalState,
        sheetContent = { modalContent() }
    ) {
        ScanScreenContent(
            isCameraPermissionGranted = cameraPermissionState.status.isGranted,
            isFlashEnabled = isFlashEnabled,
            onCanDetectQRCodeChanged = {
                canDetectQRCode = it
            },
            onFlashClicked = {
                isFlashEnabled = !isFlashEnabled
            },
            onGalleryClicked = {
                galleryLauncher.launch("image/*")
            },
            onBarcodeDetected = {
                scope.launch {
                    if (canDetectQRCode) {
                        canDetectQRCode = false
                        if (isVibrationAfterScanEnabled) {
                            context.vibrateScan()
                        }

                        modalTypeEvent.emit(ModalType.ScanSuccessModal(it))
                    }
                }
            },
            navigateToSettings = navigateToSettings
        )
    }
}