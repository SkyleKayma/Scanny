package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun CreateQRScaffold(
    scaffoldState: ScaffoldState,
    title: String,
    onClickHomeButton: () -> Boolean,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
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