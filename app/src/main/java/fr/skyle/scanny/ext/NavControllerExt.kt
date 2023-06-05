package fr.skyle.scanny.ext

import androidx.navigation.NavController

fun NavController.popUpToRouteThenNavigate(popBackTo: String, destination: String, isInclusive: Boolean = true) {
    navigate(destination) {
        popUpTo(popBackTo) {
            inclusive = isInclusive
        }
        launchSingleTop = true
    }
}