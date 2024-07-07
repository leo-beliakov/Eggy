package com.leoapps.eggy.setup.presentation.model

import androidx.annotation.StringRes
import com.leoapps.eggy.progress.presentation.model.ActionButtonState
import nl.dionsegijn.konfetti.core.Party

data class BoilProgressUiState(
    @StringRes val titleResId: Int,
    val buttonState: ActionButtonState = ActionButtonState.START,
    val finishCelebrationConfig: List<Party>? = null,
    val selectedDialog: Dialog? = null,
) {
    val isInProgress: Boolean
        get() = buttonState == ActionButtonState.STOP

    enum class Dialog {
        CANCELATION,
        RATIONALE,
        RATIONALE_GO_TO_SETTINGS,
    }
}

