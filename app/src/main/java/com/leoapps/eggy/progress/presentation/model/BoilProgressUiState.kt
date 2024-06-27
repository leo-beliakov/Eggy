package com.leoapps.eggy.setup.presentation.model

import androidx.annotation.StringRes
import com.leoapps.eggy.R

data class BoilProgressUiState(
    val availableSizes: List<EggSizeUi> = EggSizeUi.entries,
    val selectedSize: EggSizeUi? = null,
    val buttonState: ActionButtonState = ActionButtonState.START,
    val showCancelationDialog: Boolean = false,
) {
    val isInProgress: Boolean
        get() = buttonState == ActionButtonState.STOP
}

enum class ActionButtonState(
    @StringRes val textResId: Int
) {
    START(R.string.progress_button_start),
    STOP(R.string.progress_button_stop),
}

