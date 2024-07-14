package com.leoapps.setup

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.setup.presentation.model.EggBoilingTypeUi
import com.leoapps.setup.presentation.model.EggSizeUi
import com.leoapps.setup.presentation.model.EggTemperatureUi
import com.leoapps.shared_res.R
import com.leoapps.ui_test.HiltTestActivity
import com.leoapps.ui_test.getString
import com.leoapps.vibration.presentation.LocalVibrationManager
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SetupScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    val vibrationManager: VibrationManager = mockk(relaxed = true)

    @Test
    fun whenParametersAreNotSelected_continueButtonIsDisabled() {
        // Arrange
        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = { _, _ -> })
                }
            }
        }

        // Assert
        composeRule.onNode(
            hasContentDescription(composeRule.getString(R.string.common_continue))
        ).assertIsNotEnabled()
    }

    @Test
    fun whenParametersAreSelected_thButtonIsEnabled() {
        // Arrange
        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = { _, _ -> })
                }
            }
        }

        //Act
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.SMALL.titleResId)).performClick()
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.SOFT.titleResId))
            .performClick()
        composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.ROOM.titleResId))
            .performClick()

        // Assert
        composeRule.onNode(
            hasContentDescription(composeRule.getString(R.string.common_continue))
        ).assertIsEnabled()
    }

    @Test
    fun canSelectOnlySingeTemperature() {
        // Arrange
        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = { _, _ -> })
                }
            }
        }

        //Act
        composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.ROOM.titleResId))
            .performClick()
        composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.FRIDGE.titleResId))
            .performClick()

        // Assert
        composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.ROOM.titleResId))
            .assertIsOff()
        composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.FRIDGE.titleResId))
            .assertIsOn()
    }

    @Test
    fun canSelectOnlySingeSize() {
        // Arrange
        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = { _, _ -> })
                }
            }
        }

        //Act
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.SMALL.titleResId)).performClick()
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.MEDIUM.titleResId))
            .performClick()
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.LARGE.titleResId)).performClick()

        // Assert
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.SMALL.titleResId)).assertIsOff()
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.MEDIUM.titleResId)).assertIsOff()
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.LARGE.titleResId)).assertIsOn()
    }

    @Test
    fun canSelectOnlySingeType() {
        // Arrange
        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = { _, _ -> })
                }
            }
        }

        //Act
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.SOFT.titleResId))
            .performClick()
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.MEDIUM.titleResId))
            .performClick()
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.HARD.titleResId))
            .performClick()

        // Assert
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.SOFT.titleResId))
            .assertIsOff()
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.MEDIUM.titleResId))
            .assertIsOff()
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.HARD.titleResId))
            .assertIsOn()
    }

    @Test
    fun whenContinueClicked_shouldNavigateForward() {
        // Arrange
        val onContinueClicked: (EggBoilingType, Long) -> Unit = mockk(relaxed = true)
        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = onContinueClicked)
                }
            }
        }

        // Act
        composeRule.onNodeWithText(composeRule.getString(EggSizeUi.SMALL.titleResId)).performClick()
        composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.SOFT.titleResId))
            .performClick()
        composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.ROOM.titleResId))
            .performClick()
        composeRule.onNode(hasContentDescription(composeRule.getString(R.string.common_continue)))
            .performClick()

        // Assert
        verify { onContinueClicked.invoke(any(), any()) }
    }
}
