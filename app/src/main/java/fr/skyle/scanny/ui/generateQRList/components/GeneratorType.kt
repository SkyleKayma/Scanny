package fr.skyle.scanny.ui.generateQRList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.iconId
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun GeneratorType(
    qrType: QRType,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 0.dp,
        backgroundColor = SCAppTheme.colors.backgroundPrimary,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(SCAppTheme.colors.backgroundLight)
                    .padding(16.dp)
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = qrType.iconId),
                    contentDescription = "",
                    tint = SCAppTheme.colors.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = qrType.textId),
                color = SCAppTheme.colors.textPrimary,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
fun PreviewGeneratorType() {
    SCTheme {
        GeneratorType(
            qrType = QRType.TEXT,
            onClick = {}
        )
    }
}