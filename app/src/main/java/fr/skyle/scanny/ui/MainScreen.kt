package fr.skyle.scanny.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fr.skyle.scanny.R
import fr.skyle.scanny.navigation.BottomBarScreens
import fr.skyle.scanny.navigation.ScannyNavHost
import fr.skyle.scanny.navigation.screensWithBottomAppBar
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {

    // Context
    val context = LocalContext.current.applicationContext

    // Nav
    val navController = rememberAnimatedNavController()
    val items = listOf(BottomBarScreens.QRScan, BottomBarScreens.QRHistory, BottomBarScreens.QRGenerator, BottomBarScreens.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // System UI Controller
    val systemUiController = rememberSystemUiController()

    // Colors of system bars
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(context.getColor(R.color.sc_transparent)),
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = Color(context.getColor(R.color.sc_background_primary)),
            darkIcons = true
        )
    }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(), // Needed for insets
        bottomBar = {
            if (screensWithBottomAppBar.any { currentDestination?.route == it }) {
                MainBottomBar(
                    items = items,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { innerPadding ->
        ScannyNavHost(
            navHostController = navController,
            innerPadding = innerPadding
        )
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun MainBottomBar(
    items: List<BottomBarScreens>,
    navController: NavController,
    currentDestination: NavDestination?
) {
    BottomNavigation(backgroundColor = colorResource(id = R.color.sc_background_primary)) {
        var previousRoute: String? by remember { mutableStateOf(null) }
        var coroutine by remember {
            mutableStateOf<Job?>(null)
        }
        val coroutineScope = rememberCoroutineScope()

        items.forEach { screen ->
            var atEnd by remember { mutableStateOf(false) }

            val animatedVector =
                AnimatedImageVector.animatedVectorResource(id = screen.animatedIconId)
            val painterAnimated =
                rememberAnimatedVectorPainter(animatedVector, atEnd)
            val painter = rememberVectorPainter(
                image = ImageVector.vectorResource(screen.iconId)
            )

            suspend fun runResetTimer() {
                delay(animatedVector.totalDuration.toLong() + 100L) // Duration of the animation

                atEnd = !atEnd
            }

            BottomNavigationItem(
                selectedContentColor = colorResource(id = R.color.sc_primary),
                unselectedContentColor = colorResource(id = R.color.sc_icon_secondary),
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = if (atEnd) painterAnimated else painter,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(screen.resourceId), style = MaterialTheme.typography.h5)
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    if (screen.route != previousRoute) {
                        previousRoute = screen.route

                        if (!atEnd) {
                            atEnd = true

                            // Start the timer to reset animation
                            coroutine?.cancel()
                            coroutine = coroutineScope.launch {
                                try {
                                    runResetTimer()
                                } catch (e: CancellationException) {
                                    atEnd = false
                                    throw e
                                } catch (e: Exception) {
                                    atEnd = false
                                    Timber.e(e)
                                }
                            }
                        }
                    }

                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true

                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}