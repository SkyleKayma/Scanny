package fr.skyle.scanny.ui.screen.scan.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.theme.SCTheme

@Composable
fun ScanScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
private fun PreviewScanScreen() {
    SCTheme {
        ScanScreen()
    }
}