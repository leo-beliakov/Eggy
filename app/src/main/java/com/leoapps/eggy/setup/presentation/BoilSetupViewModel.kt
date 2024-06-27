package com.leoapps.eggy.setup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.setup.domain.CalculateBoilingTimeUseCase
import com.leoapps.eggy.setup.domain.model.EggBoilingType
import com.leoapps.eggy.setup.presentation.model.BoilSetupUiEvent
import com.leoapps.eggy.setup.presentation.model.BoilSetupUiState
import com.leoapps.eggy.setup.presentation.model.EggBoilingTypeUi
import com.leoapps.eggy.setup.presentation.model.EggSizeUi
import com.leoapps.eggy.setup.presentation.model.EggTemperatureUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoilSetupViewModel @Inject constructor(
    private val calculateBoilingTime: CalculateBoilingTimeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(BoilSetupUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<BoilSetupUiEvent>()
    val events = _events.asSharedFlow()

    init {
        state.distinctUntilChanged { old, new ->
            old.selectedTemperature == new.selectedTemperature &&
                    old.selectedSize == new.selectedSize &&
                    old.selectedType == new.selectedType
        }.onEach {
            _state.update {
                val time = calculateBoilingTime(
                    temperature = it.selectedTemperature?.temperature,
                    size = it.selectedSize?.size,
                    type = it.selectedType?.type,
                )

                it.copy(
                    calculatedTime = time,
                    calculatedTimeText = convertMsToText(time),
                    nextButtonEnabled = it.selectedTemperature != null &&
                            it.selectedSize != null &&
                            it.selectedType != null
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onSizeSelected(eggSize: EggSizeUi) {
        _state.update {
            it.copy(selectedSize = eggSize)
        }
    }

    fun onTypeSelected(eggBoilingType: EggBoilingTypeUi) {
        _state.update {
            it.copy(selectedType = eggBoilingType)
        }
    }

    fun onTemperatureSelected(eggTemperature: EggTemperatureUi) {
        _state.update {
            it.copy(selectedTemperature = eggTemperature)
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            _events.emit(
                BoilSetupUiEvent.OpenProgressScreen(
                    eggType = state.value.selectedType?.type ?: EggBoilingType.MEDIUM,
                    calculatedTime = state.value.calculatedTime
                )
            )
        }
    }

    private fun convertMsToText(ms: Long): String {
        val totalSeconds = ms / MILLIS_IN_SECOND
        val minutes = (totalSeconds / SECONDS_IN_MINUTE) % MINUTES_IN_HOUR
        val seconds = totalSeconds % SECONDS_IN_MINUTE
        return String.format(TIME_FORMAT, minutes, seconds)
    }
}

private const val MILLIS_IN_SECOND = 1000
private const val SECONDS_IN_MINUTE = 60
private const val MINUTES_IN_HOUR = 60
private const val TIME_FORMAT = "%02d:%02d"