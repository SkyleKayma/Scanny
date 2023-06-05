package fr.skyle.scanny.ui.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.data.vo.BarcodeData
import fr.skyle.scanny.enums.DateFormat
import fr.skyle.scanny.ext.format
import fr.skyle.scanny.ext.iconId
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCTopAppBarWithHomeButton

@Composable
fun HistoryScreenContent(
    barcodes: () -> List<BarcodeData>,
    navigateToBarcodeDetail: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    // Context
    val context = LocalContext.current

    // Remember
    val mBarcodes by remember(barcodes()) {
        mutableStateOf(barcodes())
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SCAppTheme.colors.nuance90)
            .systemBarsPadding(),
        scaffoldState = rememberScaffoldState(),
        topBar = {
            SCTopAppBarWithHomeButton(
                modifier = Modifier.background(SCAppTheme.colors.nuance90),
                title = stringResource(id = R.string.history_title),
                onClickHomeButton = navigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(SCAppTheme.colors.nuance90),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp)
        ) {
            items(mBarcodes) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SCAppTheme.colors.nuance100)
                        .clickable {
                            navigateToBarcodeDetail(it.id)
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(SCAppTheme.colors.nuance90)
                            .padding(8.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = it.type.iconId),
                            contentDescription = "",
                            tint = SCAppTheme.colors.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = it.type.textId),
                            style = SCAppTheme.typography.h3,
                            color = SCAppTheme.colors.primary,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it.scanDate.format(context, DateFormat.dd_MM_yyyy_HHmm) ?: "",
                            style = SCAppTheme.typography.caption,
                            color = SCAppTheme.colors.nuance40,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SCAppTheme.colors.nuance90)
                                .padding(10.dp),
                            text = it.content ?: "",
                            style = SCAppTheme.typography.body3,
                            color = SCAppTheme.colors.nuance10,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "",
                        tint = SCAppTheme.colors.primary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewHistoryScreenContent() {
    SCTheme {
        HistoryScreenContent(
            barcodes = { listOf() },
            navigateToBarcodeDetail = {},
            navigateBack = {}
        )
    }
}