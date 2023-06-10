package fr.skyle.scanny.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun SCLoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SCAppTheme.colors.nuance90),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = SCAppTheme.colors.primary
        )
    }
}

@Preview
@Composable
private fun PreviewSCLoadingView() {
    SCTheme {
        SCLoadingView()
    }
}