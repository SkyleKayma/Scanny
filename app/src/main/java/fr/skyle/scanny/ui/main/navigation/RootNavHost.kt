package fr.skyle.scanny.ui.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import fr.skyle.scanny.ui.screen.scan.entry.scanRouteImpl

@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
) {
    val backStack = remember {
        mutableStateListOf<Route>(Route.Scan)
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = entryProvider {
            scanRouteImpl(backStack = backStack)
        },
    )
}