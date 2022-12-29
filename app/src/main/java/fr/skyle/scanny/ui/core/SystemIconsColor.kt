package fr.skyle.scanny.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SystemIconsColor(
    statusBarColor: Color? = null,
    statusBarDarkIcons: Boolean = false,
    navigationBarColor: Color? = null,
    navigationBarDarkIcons: Boolean = true,
    key: Any = Unit,
    systemUiController: SystemUiController = rememberSystemUiController()
) {
    LaunchedEffect(key1 = key, block = {
        statusBarColor?.let {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = statusBarDarkIcons
            )
        } ?: run {
            systemUiController.statusBarDarkContentEnabled = statusBarDarkIcons
        }

        navigationBarColor?.let {
            systemUiController.setNavigationBarColor(
                color = navigationBarColor,
                darkIcons = navigationBarDarkIcons
            )
        } ?: run {
            systemUiController.navigationBarDarkContentEnabled = navigationBarDarkIcons
        }
    })
}