package com.leoapps.eggy.setup.presentation.model

import com.leoapps.eggy.setup.domain.model.EggBoilingType

sealed interface BoilSetupUiEvent {

    data class OpenProgressScreen(
        val eggType: EggBoilingType,
        val calculatedTime: Long,
    ) : BoilSetupUiEvent
}