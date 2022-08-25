package fr.skyle.scanny.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.enum.QRType
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
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colors.primary)
        ) {
            Image(
                modifier = Modifier.padding(12.dp),
                painter = painterResource(id = qrType.iconId),
                contentDescription = stringResource(id = qrType.textId),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_white))
            )
        }
    }
}

@Composable
@Preview
fun PreviewQRTypeSquareCell() {
    ScannyTheme {
        QRTypeSquareCell(qrType = QRType.TEXT)
    }
}