package com.leoapps.setup

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.ui_test.HiltTestActivity
import com.leoapps.vibration.presentation.LocalVibrationManager
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SetupScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testRule = createAndroidComposeRule<HiltTestActivity>()

    val vibrationManager: VibrationManager = mockk(relaxed = true)

    @Test
    fun whenTheScreenIsNewlyOpened_thButtonIsDisabled() {
        // Arrange
        val onContinueClicked: (EggBoilingType, Long) -> Unit = mockk(relaxed = true)
        testRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = onContinueClicked)
                }
            }
        }

        // Assert
        testRule.onNodeWithTag("buttonContinue").assertIsDeactivated()
    }
}