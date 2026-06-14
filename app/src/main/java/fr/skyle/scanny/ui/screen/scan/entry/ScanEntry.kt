package fr.skyle.scanny.ui.screen.scan.entry

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import fr.skyle.scanny.ui.main.navigation.Route
import fr.skyle.scanny.ui.screen.scan.ui.ScanRoute

fun EntryProviderScope<Route>.scanRouteImpl(
    backStack: SnapshotStateList<Route>,
) {
    entry<Route.Scan> {
        ScanRoute()
    }
}