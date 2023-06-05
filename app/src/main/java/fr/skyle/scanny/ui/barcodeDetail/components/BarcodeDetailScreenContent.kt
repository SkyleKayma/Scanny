package fr.skyle.scanny.ui.barcodeDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.R
import fr.skyle.scanny.data.vo.BarcodeData
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCTopAppBarWithHomeButton

@Composable
fun BarcodeDetailScreenContent(
    barcode: () -> BarcodeData?,
    navigateBack: () -> Unit
) {
    val mBarcodeContent by remember(barcode()) {
        mutableStateOf(barcode())
    }

    Scaffold(
        modifier = Modifier
            .background(SCAppTheme.colors.nuance90)
            .systemBarsPadding(),
        scaffoldState = rememberScaffoldState(),
        topBar = {
            SCTopAppBarWithHomeButton(
                modifier = Modifier.background(SCAppTheme.colors.nuance90),
                title = stringResource(id = R.string.history_title),
                onClickHomeButton = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(SCAppTheme.colors.nuance90)
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewBarcodeDetailScreenContent() {
    SCTheme {
        BarcodeDetailScreenContent(
            barcode = { null },
            navigateBack = {}
        )
    }
}