package com.leoapps.eggy.progress.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.leoapps.eggy.base.presentation.utils.convertMsToText
import com.leoapps.eggy.progress.presentation.model.BoilProgressUiEvent
import com.leoapps.eggy.progress.service.BoilProgressService
import com.leoapps.eggy.progress.service.TimerStatusUpdate
import com.leoapps.eggy.setup.presentation.model.ActionButtonState
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoilProgressViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<BoilProgressScreenDestination>()

    private val _state = MutableStateFlow(BoilProgressUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<BoilProgressUiEvent>()
    val events = _events.asSharedFlow()

    private var binder: BoilProgressService.MyBinder? = null
    private var serviceSubscribtionJob: Job? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as? BoilProgressService.MyBinder
            serviceSubscribtionJob = binder?.state
                ?.onEach { timerState ->
                    when (timerState) {
                        TimerStatusUpdate.Canceled,
                        TimerStatusUpdate.Finished -> {
                            _state.update {
                                it.copy(
                                    progress = 0f,
                                    progressTimeText = convertMsToText(0L),
                                    buttonState = ActionButtonState.STOP,
                                )
                            }
                        }

                        is TimerStatusUpdate.Progress -> {
                            _state.update {
                                it.copy(
                                    progress = timerState.valueMs.toFloat() / args.calculatedTime,
                                    progressTimeText = convertMsToText(timerState.valueMs),
                                )
                            }
                        }
                    }
                }?.launchIn(viewModelScope)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceSubscribtionJob?.cancel()
        }
    }

    init {
        context.bindService(
            Intent(context, BoilProgressService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE,
        )
    }

    fun onButtonClicked() {
        when (state.value.buttonState) {
            ActionButtonState.START -> onStartClicked()
            ActionButtonState.STOP -> onStopClicked()
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

    private fun onStartClicked() {
        binder?.startTimer(args.calculatedTime)
        _state.update { it.copy(buttonState = ActionButtonState.STOP) }
    }

    private fun onStopClicked() {
        binder?.stopTimer()
        _state.update { it.copy(buttonState = ActionButtonState.START) }
    }
}
