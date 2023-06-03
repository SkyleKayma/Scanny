package fr.skyle.scanny.ui.feedback

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.feedback.components.FeedbackScreenContent

@Composable
fun FeedbackScreen(
    navigateBack: () -> Unit
) {
    FeedbackScreenContent(
        navigateBack = navigateBack
    )
}

@Preview
@Composable
fun PreviewFeedbackScreen() {
    SCTheme {
        FeedbackScreen(
            navigateBack = {}
        )
    }
}