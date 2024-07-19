package com.leoapps.progress

import androidx.compose.runtime.CompositionLocalProvider
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import com.leoapps.progress.presentation.BoilProgressScreen
import com.leoapps.shared_res.R
import com.leoapps.vibration.presentation.LocalVibrationManager
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class BoilProgressScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar",
    )

    @Test
    fun `BoilProgressScreen initial state`() {
        paparazzi.snapshot {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides mockk(relaxed = true)) {
                    BoilProgressScreen(
                        onBackClicked = {},
                        onButtonClicked = {},
                        onCelebrationFinished = {},
                        onCancelationDialogConfirmed = {},
                        onRationaleDialogConfirm = {},
                        onGoToSettingsDialogConfirm = {},
                        onCancelationDialogDismissed = {},
                        state = BoilProgressUiState(
                            titleResId = R.string.common_soft_boiled_eggs,
                            boilingTime = "02:25",
                        ),
                    )
                }
            }
        }
    }

    @Test
    fun `BoilProgressScreen cancelation dialog shown`() {
        paparazzi.snapshot(
            name = "BoilProgressScreen-cancelation-dialog-shown"
        ) {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides mockk(relaxed = true)) {
                    BoilProgressScreen(
                        onBackClicked = {},
                        onButtonClicked = {},
                        onCelebrationFinished = {},
                        onCancelationDialogConfirmed = {},
                        onRationaleDialogConfirm = {},
                        onGoToSettingsDialogConfirm = {},
                        onCancelationDialogDismissed = {},
                        state = BoilProgressUiState(
                            titleResId = R.string.common_soft_boiled_eggs,
                            boilingTime = "02:25",
                            selectedDialog = BoilProgressUiState.Dialog.CANCELATION,
                        ),
                    )
                }
            }
        }
    }

    @Test
    fun `BoilProgressScreen permission rationale dialog shown`() {
        paparazzi.snapshot {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides mockk(relaxed = true)) {
                    BoilProgressScreen(
                        onBackClicked = {},
                        onButtonClicked = {},
                        onCelebrationFinished = {},
                        onCancelationDialogConfirmed = {},
                        onRationaleDialogConfirm = {},
                        onGoToSettingsDialogConfirm = {},
                        onCancelationDialogDismissed = {},
                        state = BoilProgressUiState(
                            titleResId = R.string.common_soft_boiled_eggs,
                            boilingTime = "02:25",
                            selectedDialog = BoilProgressUiState.Dialog.RATIONALE,
                        ),
                    )
                }
            }
        }
    }

    @Test
    fun `BoilProgressScreen go to settings dialog shown`() {
        paparazzi.snapshot {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides mockk(relaxed = true)) {
                    BoilProgressScreen(
                        onBackClicked = {},
                        onButtonClicked = {},
                        onCelebrationFinished = {},
                        onCancelationDialogConfirmed = {},
                        onRationaleDialogConfirm = {},
                        onGoToSettingsDialogConfirm = {},
                        onCancelationDialogDismissed = {},
                        state = BoilProgressUiState(
                            titleResId = R.string.common_soft_boiled_eggs,
                            boilingTime = "02:25",
                            selectedDialog = BoilProgressUiState.Dialog.RATIONALE_GO_TO_SETTINGS,
                        ),
                    )
                }
            }
        }
    }
}