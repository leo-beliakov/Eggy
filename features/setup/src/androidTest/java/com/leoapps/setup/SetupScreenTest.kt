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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SetupScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    val vibrationManager: VibrationManager = mockk(relaxed = true)
    val onContinueClicked: (EggBoilingType, Long) -> Unit = mockk(relaxed = true)

    @Before
    fun setup() {
        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilSetupScreen(onContinueClicked = onContinueClicked)
                }
            }
        }
    }

    @Test
    fun whenParametersAreNotSelected_continueButtonIsDisabled() {
        // Arrange
        val continueButton = composeRule.onNode(
            hasContentDescription(composeRule.getString(R.string.common_continue))
        )

        // Assert
        continueButton.assertIsNotEnabled()
    }

    @Test
    fun whenParametersAreSelected_thButtonIsEnabled() {
        // Arrange
        val sizeButton =
            composeRule.onNodeWithText(composeRule.getString(EggSizeUi.SMALL.titleResId))
        val typeButton =
            composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.SOFT.titleResId))
        val temperatureButton =
            composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.ROOM.titleResId))
        val continueButton =
            composeRule.onNode(hasContentDescription(composeRule.getString(R.string.common_continue)))

        //Act
        sizeButton.performClick()
        typeButton.performClick()
        temperatureButton.performClick()

        // Assert
        continueButton.assertIsEnabled()
    }

    @Test
    fun canSelectOnlySingleTemperature() {
        // Arrange
        val roomButton =
            composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.ROOM.titleResId))
        val fridgeButton =
            composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.FRIDGE.titleResId))

        //Act
        roomButton.performClick()
        fridgeButton.performClick()

        // Assert
        roomButton.assertIsOff()
        fridgeButton.assertIsOn()
    }

    @Test
    fun canSelectOnlySingleSize() {
        // Arrange
        val smallButton =
            composeRule.onNodeWithText(composeRule.getString(EggSizeUi.SMALL.titleResId))
        val mediumButton =
            composeRule.onNodeWithText(composeRule.getString(EggSizeUi.MEDIUM.titleResId))
        val largeButton =
            composeRule.onNodeWithText(composeRule.getString(EggSizeUi.LARGE.titleResId))

        // Act
        smallButton.performClick()
        mediumButton.performClick()
        largeButton.performClick()

        // Assert
        smallButton.assertIsOff()
        mediumButton.assertIsOff()
        largeButton.assertIsOn()
    }

    @Test
    fun canSelectOnlySingleType() {
        // Arrange
        val softButton =
            composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.SOFT.titleResId))
        val mediumButton =
            composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.MEDIUM.titleResId))
        val hardButton =
            composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.HARD.titleResId))

        // Act
        softButton.performClick()
        mediumButton.performClick()
        hardButton.performClick()

        // Assert
        softButton.assertIsOff()
        mediumButton.assertIsOff()
        hardButton.assertIsOn()
    }

    @Test
    fun whenContinueClicked_shouldNavigateForward() {
        // Arrange
        val smallButton =
            composeRule.onNodeWithText(composeRule.getString(EggSizeUi.SMALL.titleResId))
        val softButton =
            composeRule.onNodeWithText(composeRule.getString(EggBoilingTypeUi.SOFT.titleResId))
        val roomButton =
            composeRule.onNodeWithText(composeRule.getString(EggTemperatureUi.ROOM.titleResId))
        val continueButton =
            composeRule.onNode(hasContentDescription(composeRule.getString(R.string.common_continue)))

        // Act
        smallButton.performClick()
        softButton.performClick()
        roomButton.performClick()
        continueButton.performClick()

        // Assert
        verify { onContinueClicked.invoke(any(), any()) }
    }
}
