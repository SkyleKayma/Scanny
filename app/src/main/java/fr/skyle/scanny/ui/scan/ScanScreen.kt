package fr.skyle.scanny.ui.scan

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.ScanModalType
import fr.skyle.scanny.events.scanModalTypeEvent
import fr.skyle.scanny.ext.toQRCodeContent
import fr.skyle.scanny.ext.vibrateScan
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.scan.components.ScanScreenContent
import fr.skyle.scanny.ui.scanDetail.ScanDetail
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import fr.skyle.scanny.utils.scan.BarCodeHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    navigateToAppSettings: () -> Unit,
    navigateToSettings: () -> Unit,
    onShareContent: (String) -> Unit,
    onOpenLink: (QRCodeContent.UrlContent) -> Unit,
    onSendEmail: (QRCodeContent.EmailMessageContent) -> Unit,
    onSendSMS: (QRCodeContent.SMSContent) -> Unit,
    onConnectToWifi: (QRCodeContent.WiFiContent) -> Unit,
    onAddToContact: (QRCodeContent.ContactContent) -> Unit,
    viewModel: ScanViewModel = hiltViewModel()
) {
    // Set system icons color
    SystemIconsColor(
        statusBarDarkIcons = false,
        navigationBarDarkIcons = false,
        navigationBarColor = SCAppTheme.colors.transparent
    )

    // Context
    val context = LocalContext.current

    // Remember
    val scope = rememberCoroutineScope()

    var modalContent: @Composable () -> Unit by remember {
        mutableStateOf({
            Box(modifier = Modifier.fillMaxSize())
        })
    }
    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var isFlashEnabled by remember { mutableStateOf(false) }

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            try {
                BarcodeScanning.getClient()
                    .process(InputImage.fromFilePath(context, uri!!))
                    .addOnSuccessListener {
                        it.firstOrNull()?.let { barcode ->
                            // Disable scanning when we are showing results
                            BarCodeHelper.isQRCodeScanEnabled(false)

                            scope.launch {
                                // Show success modal
                                scanModalTypeEvent.emit(ScanModalType.ScanSuccessScanModal(barcode))
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

    // Flow
    val isVibrationAfterScanEnabled by viewModel.isVibrationAfterScanEnabled.collectAsState()
    val isOpenLinkAfterScanEnabled by viewModel.isOpenLinkAfterScanEnabled.collectAsState()
    val isRawContentShown by viewModel.isRawContentShown.collectAsState()

    // Permission
    LaunchedEffect(key1 = Unit, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    // Back override
    BackHandler(enabled = modalState.isVisible) {
        scope.launch {
            modalState.hide()
        }
    }

    // Re-enable scan feature
    LaunchedEffect(key1 = modalState.currentValue, block = {
        if (!modalState.isVisible) {
            BarCodeHelper.isQRCodeScanEnabled(true)
        }
    })

    // Handle modal
    LaunchedEffect(modalState) {
        scanModalTypeEvent.collectLatest { bottomSheetType ->
            when (bottomSheetType) {
                is ScanModalType.ScanSuccessScanModal -> {
                    val qrCodeContent = bottomSheetType.barcode.toQRCodeContent

                    // Vibrate
                    if (isVibrationAfterScanEnabled) {
                        context.vibrateScan()
                    }

                    if (qrCodeContent is QRCodeContent.UrlContent && isOpenLinkAfterScanEnabled) {
                        onOpenLink(qrCodeContent)

                        // Wait before enabling scan feature, otherwise it will be called multiple times before browser open
                        delay(2_000L)

                        // Enable scan feature
                        BarCodeHelper.isQRCodeScanEnabled(true)
                    } else {
                        modalContent = {
                            ScanDetail(
                                barcode = bottomSheetType.barcode,
                                onCopyContent = {
                                    clipboardManager.setText(it)
                                },
                                onShareContent = onShareContent,
                                onOpenLink = onOpenLink,
                                onSendEmail = onSendEmail,
                                onSendSMS = onSendSMS,
                                onConnectToWifi = onConnectToWifi,
                                onAddToContact = onAddToContact,
                                isRawContentShown = isRawContentShown
                            )
                        }
                        modalState.show()
                    }
                }

                null -> modalState.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(SCAppTheme.colors.backgroundBlack),
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = SCAppTheme.colors.background,
        sheetState = modalState,
        sheetContent = { modalContent() }
    ) {
        ScanScreenContent(
            isCameraPermissionGranted = cameraPermissionState.status.isGranted,
            isFlashEnabled = isFlashEnabled,
            onFlashClicked = {
                isFlashEnabled = !isFlashEnabled
            },
            onGalleryClicked = {
                galleryLauncher.launch("image/*")
            },
            navigateToAppSettings = navigateToAppSettings,
            navigateToSettings = navigateToSettings
        )
    }
}