package fr.skyle.scanny.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun ScanSuccessBottomSheet(
    barcode: Barcode? = null
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(SCAppTheme.colors.backgroundLight)
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = barcode?.rawValue ?: "",
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun PreviewScanSuccessBottomSheet() {
    SCTheme {
        ScanSuccessBottomSheet(
            barcode = null
        )
    }
}