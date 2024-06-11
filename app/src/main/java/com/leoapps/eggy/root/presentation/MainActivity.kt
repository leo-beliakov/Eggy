package com.leoapps.eggy.root.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leoapps.eggy.base.presentation.EggyTheme
import com.leoapps.eggy.progress.presentation.BoilProgressScreen
import com.leoapps.eggy.progress.presentation.BoilProgressScreenDestination
import com.leoapps.eggy.splash.presentation.BoilSetupScreen
import com.leoapps.eggy.splash.presentation.BoilSetupScreenDestination
import com.leoapps.eggy.splash.presentation.SplashScreen
import com.leoapps.eggy.splash.presentation.SplashScreenDestination

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EggyTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = SplashScreenDestination
                ) {
                    composable<SplashScreenDestination> {
                        SplashScreen(
                            onContinueClicked = {
                                navController.navigate(BoilSetupScreenDestination)
                            }
                        )
                    }
                    composable<BoilSetupScreenDestination> {
                        BoilSetupScreen(
                            onContinueClicked = {
                                navController.navigate(BoilProgressScreenDestination)
                            }
                        )
                    }
                    composable<BoilProgressScreenDestination> {
                        BoilProgressScreen(
                            onBackClicked = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}