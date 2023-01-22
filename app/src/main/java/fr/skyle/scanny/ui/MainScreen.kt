package fr.skyle.scanny.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fr.skyle.scanny.enums.ModalType
import fr.skyle.scanny.events.modalTypeEvent
import fr.skyle.scanny.ext.vibrateScan
import fr.skyle.scanny.navigation.ScannyNavHost
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.scanDetail.ScanDetail
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import fr.skyle.scanny.utils.scan.BarCodeHelper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    navigateToDataPrivacy: () -> Unit,
    navigateToRateApp: () -> Unit,
    navigateToAppSettings: () -> Unit,
    navigateToOpenium: () -> Unit,
    onShareContent: (String) -> Unit,
    onOpenLink: (QRCodeContent.UrlContent) -> Unit,
    onSendEmail: (QRCodeContent.EmailMessageContent) -> Unit,
    onSendSMS: (QRCodeContent.SMSContent) -> Unit,
    onConnectToWifi: (QRCodeContent.WiFiContent) -> Unit,
    onAddToContact: (QRCodeContent.ContactContent) -> Unit
) {
    // Context
    val context = LocalContext.current

    // Nav
    val navController = rememberAnimatedNavController()

    // Set system icons color
    SystemIconsColor(statusBarColor = SCAppTheme.colors.transparent)

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
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    // Back override
    BackHandler(enabled = modalState.isVisible) {
        scope.launch {
            modalState.hide()
        }
    }

    // Effect
    LaunchedEffect(key1 = modalState.currentValue, block = {
        if (!modalState.isVisible) {
            BarCodeHelper.isQRCodeScanEnabled(true)
        }
    })

    LaunchedEffect(modalState) {
        modalTypeEvent.collectLatest { bottomSheetType ->
            when (bottomSheetType) {
                is ModalType.ScanSuccessModal -> {
                    context.vibrateScan()
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
                            onAddToContact = onAddToContact
                        )
                    }
                    modalState.show()
                }
                null -> modalState.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = SCAppTheme.colors.background,
        sheetState = modalState,
        sheetContent = { modalContent() }
    ) {
        ScannyNavHost(
            navHostController = navController,
            navigateToDataPrivacy = navigateToDataPrivacy,
            navigateToRateApp = navigateToRateApp,
            navigateToAppSettings = navigateToAppSettings,
            navigateToOpenium = navigateToOpenium
        )
    }

//    shareQRCodeContent?.let {
//        val formattedText = shareQRCodeContent?.asFormattedString(context)?.toString() ?: ""
//
//        SCDialog(
//            title = "Que souhaitez-vous partager ?",
//            actionText1 = "Le contenu formaté",
//            actionText2 = "Le contenu brut",
//            onDismiss = {
//                shareQRCodeContent = null
//            },
//            onClickAction1 = {
//                onShareContent(formattedText)
//            },
//            onClickAction2 = {
//                onShareContent(shareQRCodeContent?.rawData ?: "")
//            }
//        )
//    }
}