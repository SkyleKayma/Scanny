package fr.skyle.scanny.ui.generateQR

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.scanny.R
import fr.skyle.scanny.data.enums.BarcodeFormat
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCTopAppBarWithHomeButton
import fr.skyle.scanny.utils.qrCode.QRCodeContent

@Composable
fun GenerateQRScreen(
    goBackToMain: () -> Boolean,
    qrCodeContent: QRCodeContent,
    viewModel: GenerateQRViewModel = hiltViewModel()
) {
    // Context
    val context = LocalContext.current

    // Remember
    val scaffoldState = rememberScaffoldState()

    // Flow
    val bitmapFlow by viewModel.bitmapSharedFlow.collectAsStateWithLifecycle()

    // TODO Move that
    viewModel.generateQRCode(qrCodeContent)

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            SCTopAppBarWithHomeButton(
                title = stringResource(id = qrCodeContent.type.textId),
                onClickHomeButton = {
                    goBackToMain()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                bitmapFlow?.asImageBitmap()?.let {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 0.dp),
                        contentScale = ContentScale.Inside,
                        bitmap = it,
                        contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.saveQRCodeAsFile(context)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.generate_qr_save))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            Toast.makeText(context, "TODO", Toast.LENGTH_LONG).show()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.generate_qr_share))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGenerateQRScreen() {
    SCTheme {
        GenerateQRScreen(
            goBackToMain = { true },
            qrCodeContent = QRCodeContent.TextContent("Text", format = BarcodeFormat.QR_CODE, rawData = null)
        )
    }
}