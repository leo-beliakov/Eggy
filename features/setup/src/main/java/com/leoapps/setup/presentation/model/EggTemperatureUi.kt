package com.leoapps.setup.presentation.model

import androidx.annotation.StringRes
import com.leoapps.base.egg.domain.model.EggTemperature
import com.leoapps.shared_res.R

enum class EggTemperatureUi(
    val temperature: EggTemperature,
    @StringRes val titleResId: Int,
) {
    ROOM(
        temperature = EggTemperature.ROOM,
        titleResId = R.string.settings_temp_room_title
    ),
    FRIDGE(
        temperature = EggTemperature.FRIDGE,
        titleResId = R.string.settings_temp_fridge_title
    )
}