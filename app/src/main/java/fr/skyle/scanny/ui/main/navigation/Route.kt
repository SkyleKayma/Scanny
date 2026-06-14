package fr.skyle.scanny.ui.main.navigation

sealed interface Route {
    data object Scan : Route
}