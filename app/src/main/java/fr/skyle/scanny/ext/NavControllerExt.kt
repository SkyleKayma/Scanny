package fr.skyle.scanny.ext

import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val request = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(request)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination

        // Add custom args to the ones defined in the route
        val newArgs = (destination.addInDefaultArgs(deepLinkMatch.matchingArgs) ?: Bundle()).apply {
            putAll(args)
        }

        // Add deeplink intent
        val intent = Intent().apply {
            setDataAndType(request.uri, request.mimeType)
            action = request.action
        }
        args.putParcelable(NavController.KEY_DEEP_LINK_INTENT, intent)

        val id = destination.id
        navigate(id, newArgs, navOptions, navigatorExtras)
    } else navigate(route, navOptions, navigatorExtras)
}