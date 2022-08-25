package fr.skyle.scanny.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import fr.skyle.scanny.theme.ScannyTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Needed for insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ScannyTheme {
                MainScreen()
            }
        }
    }
}