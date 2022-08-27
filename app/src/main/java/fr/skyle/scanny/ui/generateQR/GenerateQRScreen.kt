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
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.ext.QRCodeContent
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.ui.core.ScannyTopAppBar


@Composable
fun GenerateQRScreen(
    goBackToMain: () -> Boolean,
    qrCodeContent: QRCodeContent?,
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
        scaffoldState = scaffoldState,
        topBar = {
            ScannyTopAppBar(
                title = stringResource(id = fr.skyle.scanny.enum.QRType.TEXT.textId),
                onClickHomeButton = {
                    goBackToMain()
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                bitmapFlow?.asImageBitmap()?.let {
                    Image(
                        contentScale = ContentScale.Inside,
                        bitmap = it,
                        contentDescription = ""
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        viewModel.saveQRCodeAsFile(context)
                    }) {
                        Text(text = "Save")
                    }
                    Button(onClick = {
                        Toast.makeText(context, "TODO", Toast.LENGTH_LONG).show()
                    }) {
                        Text(text = "Share")
                    }
                }
            }
        }
    }
}