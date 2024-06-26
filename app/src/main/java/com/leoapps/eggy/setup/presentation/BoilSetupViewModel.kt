package com.leoapps.eggy.setup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.setup.domain.CalculateBoilingTimeUseCase
import com.leoapps.eggy.setup.presentation.model.BoilSetupUiState
import com.leoapps.eggy.setup.presentation.model.EggBoilingTypeUi
import com.leoapps.eggy.setup.presentation.model.EggSizeUi
import com.leoapps.eggy.setup.presentation.model.EggTemperatureUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BoilSetupViewModel @Inject constructor(
    private val calculateBoilingTime: CalculateBoilingTimeUseCase,
) : ViewModel() {

    private val _stateUi = MutableStateFlow(BoilSetupUiState())
    val stateUi = _stateUi.asStateFlow()

    init {
        stateUi
            .distinctUntilChanged { old, new ->
                old.selectedTemperature == new.selectedTemperature &&
                        old.selectedSize == new.selectedSize &&
                        old.selectedType == new.selectedType
            }.onEach {
                _stateUi.update {
                    val time = calculateBoilingTime(
                        temperature = it.selectedTemperature?.temperature,
                        size = it.selectedSize?.size,
                        type = it.selectedType?.type,
                    )

                    it.copy(calculatedTimeText = convertMsToText(time))
                }
            }.launchIn(viewModelScope)
    }

    fun onSizeSelected(eggSize: EggSizeUi) {
        _stateUi.update {
            it.copy(selectedSize = eggSize)
        }
    }

    fun onTypeSelected(eggBoilingType: EggBoilingTypeUi) {
        _stateUi.update {
            it.copy(selectedType = eggBoilingType)
        }
    }

    fun onTemperatureSelected(eggTemperature: EggTemperatureUi) {
        _stateUi.update {
            it.copy(selectedTemperature = eggTemperature)
        }
    }

    fun convertMsToText(ms: Long): String {
        val sec = ms.div(1000)
        val mins = sec.div(60)
        val minsLessThanHour = mins.rem(60)
        val secLessThanMinute = sec.rem(60)
        return "${minsLessThanHour.div(10)}${minsLessThanHour.rem(10)}:${secLessThanMinute.div(10)}${
            secLessThanMinute.rem(
                10
            )
        }"
    }
}
