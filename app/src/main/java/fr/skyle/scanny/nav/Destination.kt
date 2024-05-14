package fr.skyle.scanny.nav

import androidx.navigation.NamedNavArgument
import fr.skyle.scanny.nav.destination.RouteType

abstract class Destination<T : TemplateArgs>(private val routeType: RouteType) {
    open val route: String
        get() = routeType.name

    open fun createRoute(args: T? = null): String =
        routeType.name

    open fun args(): List<NamedNavArgument> =
        listOf()
}