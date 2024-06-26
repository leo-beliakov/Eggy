package com.leoapps.eggy.setup.presentation.model

import androidx.annotation.StringRes
import com.leoapps.eggy.R
import com.leoapps.eggy.setup.domain.model.EggSize

enum class EggSizeUi(
    val size: EggSize,
    @StringRes val titleResId: Int,
) {
    SMALL(
        size = EggSize.SMALL,
        titleResId = R.string.settings_size_s
    ),
    MEDIUM(
        size = EggSize.MEDIUM,
        titleResId = R.string.settings_size_m
    ),
    LARGE(
        size = EggSize.LARGE,
        titleResId = R.string.settings_size_l
    ),
}