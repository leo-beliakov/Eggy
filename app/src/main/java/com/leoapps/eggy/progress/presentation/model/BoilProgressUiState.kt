package com.leoapps.eggy.setup.presentation.model

data class BoilProgressUiState(
    val availableSizes: List<EggSizeUi> = EggSizeUi.entries,
    val selectedSize: EggSizeUi? = null,
)

