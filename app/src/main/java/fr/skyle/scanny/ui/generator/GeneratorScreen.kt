package fr.skyle.scanny.ui.generator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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


@Composable
fun GeneratorScreen(
    goToGenerateQRTextScreen: () -> Unit
) {

    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text(
            modifier = Modifier.padding(32.dp, 16.dp),
            text = stringResource(id = R.string.generate_what_type),
            color = MaterialTheme.colors.secondary
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(32.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                GeneratorType(QRType.TEXT) {
                    goToGenerateQRTextScreen()
                }
            }
            item {
                GeneratorType(QRType.CONTACT) {

                }
            }
            item {
                GeneratorType(QRType.URL) {

                }
            }
            item {
                GeneratorType(QRType.WIFI) {

                }
            }
            item {
                GeneratorType(QRType.EMAIL) {

                }
            }
            item {
                GeneratorType(QRType.SMS) {

                }
            }
        }
    }
}

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
        backgroundColor = colorResource(id = R.color.sc_background_popup),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = qrType.iconId),
                contentDescription = "",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_primary))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = qrType.textId),
                color = colorResource(id = R.color.sc_secondary),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
fun PreviewGeneratorType() {
    Box(modifier = Modifier.size(512.dp)) {
        GeneratorType(qrType = QRType.TEXT) {

        }
    }
}