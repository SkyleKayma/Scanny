package fr.skyle.scanny.ui.generateQRText

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.enum.QRType
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.ui.core.QRTypeSquareCell
import fr.skyle.scanny.ui.core.ScannyTextField
import fr.skyle.scanny.ui.core.ScannyTopAppBar
import kotlinx.coroutines.launch


@Composable
fun GenerateQRTextScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    goBackToMain: () -> Boolean,
    viewModel: GenerateQRTextViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ScannyTopAppBar(
                title = stringResource(id = QRType.TEXT.textId),
                onClickHomeButton = {
                    goBackToMain()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 24.dp)
                .fillMaxSize()
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QRTypeSquareCell(QRType.TEXT)

            Spacer(modifier = Modifier.height(16.dp))

            ScannyTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .height(200.dp),
                initialValue = "",
                onValueChange = {
                    viewModel.setText(it)
                },
                label = "",
                keyboardType = KeyboardType.Text
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(16.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    scope.launch {
                        if (viewModel.areDataValid()) {
//                    viewModel.createQRCode()
                            val text = "Les champs sont bons"
                            scaffoldState.snackbarHostState.showSnackbar(text)

                        } else {
                            val text = "Les champs ne sont pas bons"
                            scaffoldState.snackbarHostState.showSnackbar(text)
                        }
                    }
                }
            ) {
                Text(
                    modifier = Modifier.padding(12.dp, 6.dp),
                    text = "Créer"
                )
            }
        }
    }
}