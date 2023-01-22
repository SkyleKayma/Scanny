package fr.skyle.scanny.ui.scanDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun QRContentDisplayBodySection(
    text: AnnotatedString
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(SCAppTheme.colors.backgroundLight)
            .padding(12.dp),
        text = text,
        style = SCAppTheme.typography.body2,
        color = SCAppTheme.colors.text
    )
}

@Preview
@Composable
fun PreviewQRContentDisplayBodySection() {
    SCTheme {
        QRContentDisplayBodySection(
            text = buildAnnotatedString { append("Body") }
        )
    }
}