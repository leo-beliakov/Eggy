package com.leoapps.welcome

import androidx.activity.ComponentActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.shared_res.R
import com.leoapps.ui_test.getString
import com.leoapps.vibration.presentation.LocalVibrationManager
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import com.leoapps.welcome.presentation.WelcomeScreen
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class WelcomeScreenTest {

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    val vibrationManager: VibrationManager = mockk(relaxed = true)

    @Test
    fun whenButtonIsClicked_shouldNavigateToTheNextScreen() {
        // Arrange
        val onContinueClicked: () -> Unit = mockk(relaxed = true)
        testRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    WelcomeScreen(onContinueClicked = onContinueClicked)
                }
            }
        }

        // Act
        testRule.onNodeWithText(testRule.getString(R.string.welcome_button_continue)).performClick()

        // Assert
        verify { onContinueClicked.invoke() }
    }
}