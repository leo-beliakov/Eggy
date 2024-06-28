package com.leoapps.eggy.progress.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.leoapps.eggy.progress.presentation.model.BoilProgressUiEvent
import com.leoapps.eggy.setup.presentation.model.ActionButtonState
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoilProgressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<BoilProgressScreenDestination>()

    private val _state = MutableStateFlow(BoilProgressUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<BoilProgressUiEvent>()
    val events = _events.asSharedFlow()

    init {

    }

    fun onButtonClicked() {
        _state.update {
            it.copy(
                buttonState = when (it.buttonState) {
                    ActionButtonState.START -> ActionButtonState.STOP
                    ActionButtonState.STOP -> ActionButtonState.START
                }
            )
        }

    }

    fun onBackClicked() {
        if (state.value.isInProgress) {
            setCancelationDialogVisible(true)
        } else {
            viewModelScope.launch {
                _events.emit(BoilProgressUiEvent.NavigateBack)
            }
        }
    }

    fun onCancelationDialogDismissed() {
        setCancelationDialogVisible(false)
    }

    fun onCancelationDialogConfirmed() {
        setCancelationDialogVisible(false)

        viewModelScope.launch {
            _events.emit(BoilProgressUiEvent.NavigateBack)
        }
    }

    private fun setCancelationDialogVisible(isVisible: Boolean) {
        _state.update {
            it.copy(showCancelationDialog = isVisible)
        }
    }
}