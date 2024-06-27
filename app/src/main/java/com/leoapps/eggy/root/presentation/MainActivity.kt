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
import com.leoapps.eggy.setup.domain.model.EggBoilingType
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.eggy.welcome.presentation.BoilSetupScreenDestination
import com.leoapps.eggy.welcome.presentation.WelcomeScreen
import com.leoapps.eggy.welcome.presentation.WelcomeScreenDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EggyTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = WelcomeScreenDestination
                ) {
                    composable<WelcomeScreenDestination> {
                        WelcomeScreen(
                            onContinueClicked = {
                                navController.navigate(BoilSetupScreenDestination) {
                                    popUpTo<WelcomeScreenDestination> { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable<BoilSetupScreenDestination> {
                        BoilSetupScreen(
                            onContinueClicked = {
                                navController.navigate(
                                    BoilProgressScreenDestination(
                                        type = EggBoilingType.HARD.toString(),
                                        calculatedTime = 1234L,
                                    )
                                )
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

//TODO:
//1. Extract MainActivity composable code to a separate composable function
//2. When launch decide where to navigate:
// if first launch - open Welcome
// elif no egg is in progress - open Settings
// else open Progress
//3. Settings UI
//4. Progress UI
//5. Progress Complete Animation (Confetti library?)
