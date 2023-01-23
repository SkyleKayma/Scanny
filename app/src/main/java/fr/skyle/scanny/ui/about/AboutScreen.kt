package fr.skyle.scanny.ui.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.about.components.AboutScreenContent
import fr.skyle.scanny.ui.core.SystemIconsColor

@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
    onOpenLink: (String) -> Unit
) {
    // Set system icons color
    SystemIconsColor(
        statusBarDarkIcons = true,
        navigationBarDarkIcons = true,
        navigationBarColor = SCAppTheme.colors.transparent
    )

    AboutScreenContent(
        navigateBack = navigateBack,
        onOpenLink = onOpenLink
    )
}

@Preview
@Composable
fun PreviewAboutScreen() {
    SCTheme {
        AboutScreen(
            navigateBack = {},
            onOpenLink = {}
        )
    }
}