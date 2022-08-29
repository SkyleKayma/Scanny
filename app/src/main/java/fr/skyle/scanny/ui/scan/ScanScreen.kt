package fr.skyle.scanny.ui.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.theme.ScannyTheme


@Composable
fun ScanScreen() {
    Column(
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Scan")
    }
}

@Preview
@Composable
fun PreviewScanScreen() {
    ScannyTheme {
        ScanScreen()
    }
}