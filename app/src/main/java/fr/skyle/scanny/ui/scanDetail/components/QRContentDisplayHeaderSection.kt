package fr.skyle.scanny.ui.scanDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun QRContentDisplayHeaderSection(
    textId: Int,
    onShareClick: () -> Unit,
    onCopyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(SCAppTheme.colors.primary)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            text = stringResource(id = textId),
            style = SCAppTheme.typography.body1,
            color = SCAppTheme.colors.textLight
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onCopyClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = "",
                tint = SCAppTheme.colors.textLight
            )
        }

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onShareClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = "",
                tint = SCAppTheme.colors.textLight
            )
        }
    }
}

@Preview
@Composable
fun PreviewQRContentDisplayHeaderSection() {
    SCTheme {
        QRContentDisplayHeaderSection(
            textId = R.string.scan_detail_formatted_content,
            onShareClick = {},
            onCopyClick = {}
        )
    }
}