package fr.skyle.scanny.ui.scan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.skyle.scanny.R
import kotlinx.coroutines.launch

@Composable
fun ScanResultsModalBottomSheet(
    sheetState: ModalBottomSheetState,
    stringValue: String,
    viewModel: ScanViewModel
) {
    val scope = rememberCoroutineScope()

    BackHandler(enabled = sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
        }
    }

    LaunchedEffect(key1 = sheetState.currentValue, block = {
        if (!sheetState.isVisible) {
            viewModel.reactivateQRCodeScan()
        }
    })

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetBackgroundColor = colorResource(id = R.color.sc_background),
        sheetState = sheetState,
        sheetContent = {
            Surface(
                modifier = Modifier.wrapContentSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringValue,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    ) {}
}