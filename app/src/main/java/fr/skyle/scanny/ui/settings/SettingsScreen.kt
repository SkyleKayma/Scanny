package fr.skyle.scanny.ui.settings

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
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .background(Color.Yellow)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Settings")
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    ScannyTheme {
        SettingsScreen()
    }
}