package fr.skyle.scanny.ui.core.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fr.skyle.scanny.ext.debounceClickable
import fr.skyle.scanny.theme.SCAppTheme

@Composable
fun CustomDialog(
    imageId: Int,
    imageTintColor: Color,
    title: String,
    description: String,
    negativeText: String,
    positiveText: String,
    onClickNegativeAction: () -> Unit,
    onClickPositiveAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = onDismiss ?: onClickNegativeAction
    ) {
        Card(
            modifier = modifier.padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 10.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = SCAppTheme.colors.nuance100,
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = imageId),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(color = imageTintColor)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        style = SCAppTheme.typography.h2,
                        color = SCAppTheme.colors.nuance10
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = description,
                        textAlign = TextAlign.Center,
                        style = SCAppTheme.typography.body1,
                        color = SCAppTheme.colors.nuance10
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SCAppTheme.colors.nuance10.copy(alpha = 0.4f)),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .debounceClickable(onClick = onClickNegativeAction)
                            .padding(vertical = 12.dp),
                        text = negativeText,
                        style = SCAppTheme.typography.button,
                        color = SCAppTheme.colors.nuance40,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .debounceClickable(onClick = onClickPositiveAction)
                            .padding(vertical = 12.dp),
                        text = positiveText,
                        style = SCAppTheme.typography.button,
                        color = SCAppTheme.colors.nuance10,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}