package fr.skyle.scanny.ui.generator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ui.core.TitleText
import fr.skyle.scanny.ui.generator.components.GeneratorType


@Composable
fun GeneratorScreen(
    goToCreateQRScreen: (QRType) -> Unit
) {

    Column(
        modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        TitleText(textId = R.string.settings_title_general)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.generate_what_type),
            color = colorResource(id = R.color.sc_body),
            style = MaterialTheme.typography.body2
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                GeneratorType(QRType.TEXT) {
                    goToCreateQRScreen(QRType.TEXT)
                }
            }
            item {
                GeneratorType(QRType.CONTACT) {

                }
            }
            item {
                GeneratorType(QRType.URL) {
                    goToCreateQRScreen(QRType.URL)
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