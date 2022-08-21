package fr.skyle.scanny

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import fr.skyle.scanny.enum.FileType
import fr.skyle.scanny.ui.theme.ScannyTheme
import fr.skyle.scanny.utils.QRCodeData
import fr.skyle.scanny.utils.QRCodeHelper
import io.github.g0dkar.qrcode.QRCode
import io.github.g0dkar.qrcode.render.Colors
import timber.log.Timber


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScannyTheme {
                val qrCodeData = QRCodeData(content = "Test QRCode generation")
                val qrCode = QRCodeHelper.generateQRCode(qrCodeData)
                val bitmap = QRCodeHelper.renderQRCodeAsBitmap(qrCode, qrCodeData)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box {
                        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                contentScale = ContentScale.Inside,
                                bitmap = bitmap?.asImageBitmap() ?: ColorDrawable(Colors.AZURE).toBitmap().asImageBitmap(),
                                contentDescription = "")

                            Button(onClick = { saveAsPNG(qrCode, qrCodeData, applicationContext) }) {
                                Text(text = "Save as PNG")
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun saveAsPNG(qrCode: QRCode, qrCodeData: QRCodeData, context: Context) {
    // Save file
    try {
        QRCodeHelper.saveQRCode(qrCode, qrCodeData, FileType.PNG, context)
    } catch (e: Exception){
        Timber.e(e)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScannyTheme {
        Greeting("Android")
    }
}