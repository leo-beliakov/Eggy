package com.leoapps.setup.presentation.model

import androidx.annotation.StringRes
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.shared_res.R

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