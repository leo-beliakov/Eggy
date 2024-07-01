package com.leoapps.eggy.setup.presentation.model

import androidx.annotation.StringRes
import com.leoapps.eggy.progress.presentation.model.ActionButtonState

data class BoilProgressUiState(
    @StringRes val titleResId: Int,
    val buttonState: ActionButtonState = ActionButtonState.START,
    val showCancelationDialog: Boolean = false,
) {
    val isInProgress: Boolean
        get() = buttonState == ActionButtonState.STOP
}

