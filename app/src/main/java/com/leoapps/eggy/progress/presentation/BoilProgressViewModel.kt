package com.leoapps.eggy.progress.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.leoapps.eggy.R
import com.leoapps.eggy.base.common.theme.СonfettiOrange
import com.leoapps.eggy.base.common.theme.СonfettiPink
import com.leoapps.eggy.base.common.theme.СonfettiPurple
import com.leoapps.eggy.base.common.theme.СonfettiYellow
import com.leoapps.eggy.base.common.utils.convertMsToText
import com.leoapps.eggy.progress.presentation.model.ActionButtonState
import com.leoapps.eggy.progress.presentation.model.BoilProgressUiEvent
import com.leoapps.eggy.progress.service.BoilProgressService
import com.leoapps.eggy.progress.service.TimerStatusUpdate
import com.leoapps.eggy.setup.domain.model.EggBoilingType
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
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
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class BoilProgressViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val vibrator: VibrationManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<BoilProgressScreenDestination>()
    private val eggType = EggBoilingType.fromString(args.type) ?: EggBoilingType.MEDIUM
    private val boilingTime = args.calculatedTime

    private val _state = MutableStateFlow(BoilProgressUiState(titleResId = getTitleForEggType()))
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
                        TimerStatusUpdate.Canceled -> {
                            _events.emit(
                                BoilProgressUiEvent.UpdateTimer(
                                    progress = 0f,
                                    progressText = convertMsToText(0L),
                                )
                            )
                        }

                        TimerStatusUpdate.Finished -> {
                            _events.emit(
                                BoilProgressUiEvent.UpdateTimer(
                                    progress = 0f,
                                    progressText = convertMsToText(0L),
                                )
                            )
                            _state.update {
                                it.copy(
                                    buttonState = ActionButtonState.START,
                                    finishCelebrationConfig = getCelebrationConfig()
                                )
                            }
                            vibrator.vibratePattern(
                                pattern = longArrayOf(0, 200, 100, 300, 400, 500)
                            )
                        }

                        is TimerStatusUpdate.Progress -> {
                            _events.emit(
                                BoilProgressUiEvent.UpdateTimer(
                                    progress = timerState.valueMs / boilingTime.toFloat(),
                                    progressText = convertMsToText(timerState.valueMs),
                                )
                            )
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

    fun onCelebrationFinished() {
        _state.update {
            it.copy(finishCelebrationConfig = null)
        }
    }

    private fun setCancelationDialogVisible(isVisible: Boolean) {
        _state.update {
            it.copy(showCancelationDialog = isVisible)
        }
    }

    private fun onStartClicked() {
        binder?.startTimer(boilingTime)
        _state.update { it.copy(buttonState = ActionButtonState.STOP) }
    }

    private fun onStopClicked() {
        binder?.stopTimer()
        _state.update { it.copy(buttonState = ActionButtonState.START) }
    }

    private fun getTitleForEggType() = when (eggType) {
        EggBoilingType.SOFT -> R.string.progress_title_soft_eggs
        EggBoilingType.MEDIUM -> R.string.progress_title_medium_eggs
        EggBoilingType.HARD -> R.string.progress_title_hard_eggs
    }

    private fun getCelebrationConfig(): List<Party> {
        val party = Party(
            speed = 10f,
            maxSpeed = 30f,
            damping = 0.9f,
            angle = Angle.RIGHT - 45,
            spread = Spread.SMALL,
            colors = listOf(
                СonfettiYellow.toArgb(),
                СonfettiOrange.toArgb(),
                СonfettiPurple.toArgb(),
                СonfettiPink.toArgb(),
            ),
            emitter = Emitter(duration = 3, TimeUnit.SECONDS).perSecond(50),
            position = Position.Relative(0.0, 0.35)
        )

        return listOf(
            party,
            party.copy(
                angle = party.angle - 90, // flip angle from right to left
                position = Position.Relative(1.0, 0.35)
            ),
        )
    }
}
