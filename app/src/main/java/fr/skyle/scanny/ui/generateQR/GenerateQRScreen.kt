package fr.skyle.scanny.ui.generateQR

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.R
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.ScannyTheme
import fr.skyle.scanny.ui.core.ScannyTopAppBar


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

    // Bitmap
    val bitmapFlow by viewModel.bitmapSharedFlow.collectAsState()
    viewModel.generateQRCode(qrCodeContent)

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            ScannyTopAppBar(
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
                        }) {
                        Text(text = stringResource(id = R.string.generate_qr_save))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            Toast.makeText(context, "TODO", Toast.LENGTH_LONG).show()
                        }) {
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
    ScannyTheme {
        GenerateQRScreen(
            goBackToMain = { true },
            qrCodeContent = QRCodeContent.TextContent("Text")
        )
    }
}