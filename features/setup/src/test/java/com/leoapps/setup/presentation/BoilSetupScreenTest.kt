package com.leoapps.setup.presentation

import androidx.compose.runtime.CompositionLocalProvider
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.setup.presentation.model.BoilSetupUiState
import com.leoapps.setup.presentation.model.EggBoilingTypeUi
import com.leoapps.setup.presentation.model.EggSizeUi
import com.leoapps.setup.presentation.model.EggTemperatureUi
import com.leoapps.vibration.presentation.LocalVibrationManager
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class BoilSetupScreenTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar",
    )

    @Test
    fun `BoilSetupScreen initial state`() {
        paparazzi.snapshot {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides mockk(relaxed = true)) {
                    BoilSetupScreen(
                        onNextClicked = {},
                        onSizeSelected = {},
                        onTypeSelected = {},
                        onTemperatureSelected = {},
                        state = BoilSetupUiState(),
                    )
                }
            }
        }
    }

    @Test
    fun `BoilSetupScreen parameters selected state`() {
        paparazzi.snapshot {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides mockk(relaxed = true)) {
                    BoilSetupScreen(
                        onNextClicked = {},
                        onSizeSelected = {},
                        onTypeSelected = {},
                        onTemperatureSelected = {},
                        state = BoilSetupUiState(
                            selectedSize = EggSizeUi.MEDIUM,
                            selectedType = EggBoilingTypeUi.HARD,
                            selectedTemperature = EggTemperatureUi.ROOM,
                            calculatedTime = 120_000L,
                            calculatedTimeText = "02:00",
                            nextButtonEnabled = true,
                        ),
                    )
                }
            }
        }
    }
}