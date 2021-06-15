package dev.sourabh.programmerhumor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.sourabh.programmerhumor.ui.details.ImageViewerScreen
import dev.sourabh.programmerhumor.ui.home.HomeScreen
import dev.sourabh.programmerhumor.ui.theme.ProgrammerHumorTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkColors = MaterialTheme.colors.isLight

            val navController = rememberNavController()

            SideEffect {
                systemUiController.setStatusBarColor(Color.Transparent, useDarkColors)
                systemUiController.setNavigationBarColor(Color.Transparent, useDarkColors)
            }

            ProgrammerHumorTheme {
                ProvideWindowInsets {

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(Modifier.statusBarsPadding(), navController)
                        }

                        composable("image_view") {
                            ImageViewerScreen(navController, Modifier.navigationBarsPadding())
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("Deleting cache")
        this.externalCacheDir?.deleteRecursively()
    }
}