package fr.skyle.scanny.ui.generator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ui.generator.components.GeneratorType


@Composable
fun GeneratorScreen(
    goToCreateQRScreen: (QRType) -> Unit
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