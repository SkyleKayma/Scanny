package fr.skyle.scanny.ui.generateQR.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.data.enums.BarcodeType
import fr.skyle.scanny.ext.iconId
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun QRTypeSquareCell(
    barcodeType: BarcodeType,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(100),
        elevation = 0.dp,
        backgroundColor = SCAppTheme.colors.nuance100,
    ) {
        Icon(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(id = barcodeType.iconId),
            contentDescription = stringResource(id = barcodeType.textId),
            tint = SCAppTheme.colors.primary
        )
    }
}

@Composable
@Preview
fun PreviewQRTypeSquareCell() {
    SCTheme {
        QRTypeSquareCell(
            barcodeType = BarcodeType.TEXT
        )
    }
}