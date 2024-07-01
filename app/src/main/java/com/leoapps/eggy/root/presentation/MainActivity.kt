package com.leoapps.eggy.root.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leoapps.eggy.base.common.theme.EggyTheme
import com.leoapps.eggy.progress.presentation.BoilProgressScreen
import com.leoapps.eggy.progress.presentation.BoilProgressScreenDestination
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.eggy.welcome.presentation.BoilSetupScreenDestination
import com.leoapps.eggy.welcome.presentation.WelcomeScreen
import com.leoapps.eggy.welcome.presentation.WelcomeScreenDestination
import dagger.hilt.android.AndroidEntryPoint

private const val NAVIGATION_ANIM_DURATION = 600

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EggyTheme {
                val navController = rememberNavController()
                val fadeAnimationSpec = tween<Float>(
                    durationMillis = NAVIGATION_ANIM_DURATION,
                    easing = LinearEasing
                )
                val slideAnimationSpec = tween<IntOffset>(
                    durationMillis = NAVIGATION_ANIM_DURATION,
                    easing = LinearEasing
                )

                NavHost(
                    navController = navController,
                    startDestination = WelcomeScreenDestination
                ) {
                    composable<WelcomeScreenDestination>(
                        exitTransition = { fadeOut(fadeAnimationSpec) }
                    ) {
                        WelcomeScreen(
                            onContinueClicked = {
                                navController.navigate(BoilSetupScreenDestination) {
                                    popUpTo<WelcomeScreenDestination> { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable<BoilSetupScreenDestination>(
                        enterTransition = { fadeIn(fadeAnimationSpec) },
                        exitTransition = { fadeOut(fadeAnimationSpec) }
                    ) {
                        BoilSetupScreen(
                            onContinueClicked = {
                                navController.navigate(
                                    BoilProgressScreenDestination(
                                        type = it.type.toString(),
                                        calculatedTime = it.calculatedTime,
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable<BoilProgressScreenDestination>(
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = slideAnimationSpec
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = slideAnimationSpec
                            )
                        }
                    ) {
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
//5. Progress Complete Animation (Confetti library?)
