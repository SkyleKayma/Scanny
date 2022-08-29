package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CreateQRScaffold(
    scaffoldState: ScaffoldState,
    title: String,
    onClickHomeButton: () -> Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            ScannyTopAppBar(
                title = title,
                onClickHomeButton = {
                    onClickHomeButton()
                }
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}