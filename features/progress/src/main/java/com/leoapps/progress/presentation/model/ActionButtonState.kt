package com.leoapps.progress.presentation.model

import androidx.annotation.StringRes
import com.leoapps.shared_res.R

enum class ActionButtonState(
    @StringRes val textResId: Int
) {
    START(R.string.progress_button_start),
    STOP(R.string.progress_button_stop),
}