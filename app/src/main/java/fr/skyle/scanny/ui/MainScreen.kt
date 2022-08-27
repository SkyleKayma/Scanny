package fr.skyle.scanny.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.Route
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import fr.skyle.scanny.R
import fr.skyle.scanny.navigation.BottomBarScreens
import fr.skyle.scanny.ui.destinations.Destination
import fr.skyle.scanny.ui.destinations.DirectionDestination
import kotlinx.coroutines.*
import timber.log.Timber


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreen() {

    // Context
    val context = LocalContext.current.applicationContext

    // Nav
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()
    val items = listOf(BottomBarScreens.QRScan, BottomBarScreens.QRHistory, BottomBarScreens.QRGenerator, BottomBarScreens.Settings)

    // System UI Controller
    val systemUiController = rememberSystemUiController()

    // Colors of system bars
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(context.getColor(R.color.sc_transparent)),
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = Color(context.getColor(R.color.sc_background_popup)),
            darkIcons = true
        )
    }

    MainScaffold(
        modifier = Modifier.systemBarsPadding(), // Needed for insets
        startRoute = NavGraphs.root.startRoute,
        navController = navController,
        bottomBar = { dest ->
            if (dest in BottomBarScreens.values().map { it.direction }) {
                MainBottomBar(
                    items,
                    navController
                )
            }
        },
        topBar = null
    ) { innerPadding ->
        DestinationsNavHost(
            engine = engine,
            navController = navController,
            navGraph = NavGraphs.root,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

var coroutine = mutableStateOf<Job?>(null)

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    startRoute: Route,
    navController: NavHostController,
    topBar: @Composable ((Destination, NavBackStackEntry?) -> Unit)?,
    bottomBar: @Composable ((Destination) -> Unit)?,
    content: @Composable (PaddingValues) -> Unit,
) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination
    val navBackStackEntry = navController.currentBackStackEntry

    Scaffold(
        modifier = modifier,
        topBar = {
            topBar?.let { topBar(destination, navBackStackEntry) }
        },
        bottomBar = {
            bottomBar?.let { bottomBar(destination) }
        },
        content = content
    )
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun MainBottomBar(
    items: List<BottomBarScreens>,
    navController: NavHostController
) {
    BottomNavigation(backgroundColor = colorResource(id = R.color.sc_background_popup)) {
        var previousRoute: DirectionDestination? by remember { mutableStateOf(null) }

        items.forEach { destination ->
            var atEnd by remember { mutableStateOf(false) }

            val animatedVector =
                AnimatedImageVector.animatedVectorResource(id = destination.animatedIconId)
            val painterAnimated =
                rememberAnimatedVectorPainter(animatedVector, atEnd)
            val painter = rememberVectorPainter(
                image = ImageVector.vectorResource(destination.iconId)
            )

            suspend fun runResetTimer() {
                delay(animatedVector.totalDuration.toLong() + 100L) // Duration of the animation

                atEnd = !atEnd
            }

            val isCurrentDestOnBackStack = navController.isRouteOnBackStack(destination.direction)

            BottomNavigationItem(
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = colorResource(id = R.color.sc_text_secondary),
                selected = isCurrentDestOnBackStack,
                onClick = {
                    if (isCurrentDestOnBackStack) {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        navController.popBackStack(destination.direction, false)
                        return@BottomNavigationItem
                    }

                    if (destination.direction != previousRoute) {
                        previousRoute = destination.direction

                        if (!atEnd) {
                            atEnd = !atEnd

                            // Start the timer to reset animation
                            coroutine.value?.cancel()
                            coroutine = mutableStateOf(
                                GlobalScope.launch {
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
                            )
                        }
                    }

                    navController.navigate(destination.direction) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true

                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = if (atEnd) painterAnimated else painter,
                        contentDescription = stringResource(destination.textId)
                    )
                },
                label = {
                    Text(stringResource(destination.textId), style = MaterialTheme.typography.h5)
                },
            )
        }
    }
}