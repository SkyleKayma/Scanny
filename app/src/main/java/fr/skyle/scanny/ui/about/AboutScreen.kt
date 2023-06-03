package fr.skyle.scanny.ui.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.about.components.AboutScreenContent

@Composable
fun AboutScreen(
    navigateBack: () -> Unit
) {
    AboutScreenContent(
        navigateBack = navigateBack
    )
}

@Preview
@Composable
fun PreviewAboutScreen() {
    SCTheme {
        AboutScreen(
            navigateBack = {}
        )
    }
}