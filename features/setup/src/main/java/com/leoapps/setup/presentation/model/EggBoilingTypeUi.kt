package com.leoapps.setup.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.shared_res.R

enum class EggBoilingTypeUi(
    val type: EggBoilingType,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
) {
    SOFT(
        type = EggBoilingType.SOFT,
        titleResId = R.string.settings_type_soft_title,
        iconResId = R.drawable.egg_soft,
    ),
    MEDIUM(
        type = EggBoilingType.MEDIUM,
        titleResId = R.string.settings_type_medium_title,
        iconResId = R.drawable.egg_medium,
    ),
    HARD(
        type = EggBoilingType.HARD,
        titleResId = R.string.settings_type_hard_title,
        iconResId = R.drawable.egg_hard,
    ),
}