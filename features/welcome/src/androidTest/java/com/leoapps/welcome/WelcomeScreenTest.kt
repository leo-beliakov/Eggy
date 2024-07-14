package com.leoapps.welcome

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.vibration.presentation.LocalVibrationManager
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import com.leoapps.welcome.presentation.WelcomeScreen
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val vibrationManager: VibrationManager = mockk(relaxed = true)

    @Test
    fun whenButtonIsClicked_shouldNavigateToTheNextScreen() {
        // Arrange
        val onContinueClicked: () -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    WelcomeScreen(onContinueClicked = onContinueClicked)
                }
            }
        }

        // Act
        composeTestRule.onNode(hasText("Let's cook!")).performClick()

        // Assert
        verify { onContinueClicked.invoke() }
    }
}