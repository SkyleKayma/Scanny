package fr.skyle.scanny.ui.generateQR.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.iconId
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.ScannyTheme

@Composable
fun QRTypeSquareCell(
    qrType: QRType,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(100),
        elevation = 0.dp,
        backgroundColor = colorResource(id = R.color.sc_background_icon)
    ) {
        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(id = qrType.iconId),
            contentDescription = stringResource(id = qrType.textId),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_icon_primary))
        )
    }
}

@Composable
@Preview
fun PreviewQRTypeSquareCell() {
    ScannyTheme {
        QRTypeSquareCell(qrType = QRType.TEXT)
    }
}