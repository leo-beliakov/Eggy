package com.leoapps.eggy.setup.presentation.model

const val EMPTY_CALCULATED_TIME = "00:00"

data class BoilSetupUiState(
    val availableSizes: List<EggSizeUi> = EggSizeUi.entries,
    val selectedSize: EggSizeUi? = null,
    val availableTypes: List<EggBoilingTypeUi> = EggBoilingTypeUi.entries,
    val selectedType: EggBoilingTypeUi? = null,
    val availableTemperatures: List<EggTemperatureUi> = EggTemperatureUi.entries,
    val selectedTemperature: EggTemperatureUi? = null,
    val calculatedTimeText: String = EMPTY_CALCULATED_TIME,
)

