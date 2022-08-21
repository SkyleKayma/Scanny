package fr.skyle.scanny.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fr.skyle.scanny.navigation.BottomBarScreens
import fr.skyle.scanny.navigation.ScannyNavHost
import fr.skyle.scanny.navigation.screensWithBottomAppBar


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    // Context
    val appContext = LocalContext.current.applicationContext

    // Nav
    val navController = rememberAnimatedNavController()
    val items = listOf(BottomBarScreens.QRScan, BottomBarScreens.QRGenerator, BottomBarScreens.QRHistory, BottomBarScreens.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.systemBarsPadding(), // Needed for insets
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
            innerPadding = innerPadding,
            mainViewModel = viewModel
        )
    }

//    val qrCodeData = QRCodeData(content = "Test QRCode generation")
//    val qrCode = QRCodeHelper.generateQRCode(qrCodeData)
//    val bitmap = QRCodeHelper.renderQRCodeAsBitmap(qrCode, qrCodeData)
//
//    // A surface container using the 'background' color from the theme
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colors.background
//    ) {
//        Box {
//            Column(Modifier.fillMaxSize()) {
//                Image(
//                    contentScale = ContentScale.Inside,
//                    bitmap = bitmap?.asImageBitmap()
//                        ?: ColorDrawable(Colors.AZURE).toBitmap().asImageBitmap(),
//                    contentDescription = ""
//                )
//
//                Button(onClick = {
//                    saveAsPNG(
//                        qrCode,
//                        qrCodeData,
//                        appContext
//                    )
//                }) {
//                    Text(text = "Save as PNG")
//                }
//            }
//        }
//    }
}

@Composable
private fun MainBottomBar(
    items: List<BottomBarScreens>,
    navController: NavController,
    currentDestination: NavDestination?
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.secondary) {
        items.forEach { screen ->
            BottomNavigationItem(
                selectedContentColor = Color.White,
                icon = {
                    Icon(painter = painterResource(id = screen.iconId), contentDescription = null)
                },
                label = {
                    Text(stringResource(screen.resourceId), style = MaterialTheme.typography.h5)
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true

                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}