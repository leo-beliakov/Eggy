package com.leoapps.eggy.setup.presentation.model

import androidx.annotation.StringRes
import com.leoapps.eggy.R
import com.leoapps.eggy.setup.domain.model.EggTemperature

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