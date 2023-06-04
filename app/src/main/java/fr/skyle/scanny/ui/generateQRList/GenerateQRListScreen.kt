package fr.skyle.scanny.ui.generateQRList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.data.enums.QRType
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.generateQRList.components.GeneratorType
import fr.skyle.scanny.ui.settings.components.SettingsTitleText

@Composable
fun GenerateQRListScreen(
    goToCreateQRScreen: (QRType) -> Unit
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        SettingsTitleText(stringResource(id = R.string.generate_title))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.generate_what_type),
            color = SCAppTheme.colors.primary,
            style = SCAppTheme.typography.body2
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
                    goToCreateQRScreen(QRType.CONTACT)
                }
            }
            item {
                GeneratorType(QRType.URL) {
                    goToCreateQRScreen(QRType.URL)
                }
            }
            item {
                GeneratorType(QRType.WIFI) {
                    goToCreateQRScreen(QRType.WIFI)
                }
            }
            item {
                GeneratorType(QRType.EMAIL) {
                    goToCreateQRScreen(QRType.EMAIL)
                }
            }
            item {
                GeneratorType(QRType.SMS) {
                    goToCreateQRScreen(QRType.SMS)
                }
            }
        }
    }
}