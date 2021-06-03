package dev.sourabh.programmerhumor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.sourabh.programmerhumor.ui.home.HomeScreen
import dev.sourabh.programmerhumor.ui.theme.ProgrammerHumorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkColors = MaterialTheme.colors.isLight

            SideEffect {
                systemUiController.setStatusBarColor(Color.Transparent, useDarkColors)
                systemUiController.setNavigationBarColor(Color.Transparent, useDarkColors)
            }

            ProgrammerHumorTheme {
                ProvideWindowInsets {
                    HomeScreen(Modifier.statusBarsPadding())
                }
            }
        }
    }
}
