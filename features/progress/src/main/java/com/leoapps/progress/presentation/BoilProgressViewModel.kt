package com.leoapps.progress.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.permissions.model.PermissionStatus
import com.leoapps.base.utils.convertMsToTimerText
import com.leoapps.base_ui.theme.СonfettiOrange
import com.leoapps.base_ui.theme.СonfettiPink
import com.leoapps.base_ui.theme.СonfettiPurple
import com.leoapps.base_ui.theme.СonfettiYellow
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import com.leoapps.progress.platform.service.BoilProgressService
import com.leoapps.progress.platform.service.TimerStatusUpdate
import com.leoapps.progress.presentation.model.ActionButtonState
import com.leoapps.progress.presentation.model.BoilProgressUiEvent
import com.leoapps.shared_res.R
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
import javax.inject.Inject

@Stable // https://issuetracker.google.com/issues/280284177
@HiltViewModel
class BoilProgressViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val vibrationManager: VibrationManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<BoilProgressScreenDestination>()
    private val eggType = EggBoilingType.fromString(args.type) ?: EggBoilingType.MEDIUM
    private val boilingTime = args.calculatedTime

    private val _state = MutableStateFlow(getInitialState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<BoilProgressUiEvent>()
    val events = _events.asSharedFlow()

    private var binder: BoilProgressService.MyBinder? = null
    private var serviceSubscribtionJob: Job? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as? BoilProgressService.MyBinder
            serviceSubscribtionJob = collectServiceState()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
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
            showDoalog(BoilProgressUiState.Dialog.CANCELATION)
        } else {
            viewModelScope.launch {
                _events.emit(BoilProgressUiEvent.NavigateBack)
            }
        }
    }

    fun onCancelationDialogDismissed() {
        showDoalog(null)
    }

    fun onCancelationDialogConfirmed() {
        showDoalog(null)
        viewModelScope.launch { _events.emit(BoilProgressUiEvent.NavigateBack) }
        binder?.stopTimer()
    }

    fun onCelebrationFinished() {
        _state.update {
            it.copy(finishCelebrationConfig = null)
        }
    }

    fun onPermissionResult(result: PermissionStatus) {
        when (result) {
            PermissionStatus.GRANTED -> {
                binder?.startTimer(boilingTime, eggType)
                _state.update { it.copy(buttonState = ActionButtonState.STOP) }
            }

            PermissionStatus.DENIED -> {
                showDoalog(BoilProgressUiState.Dialog.RATIONALE)
            }

            PermissionStatus.DONT_ASK_AGAIN -> {
                showDoalog(BoilProgressUiState.Dialog.RATIONALE_GO_TO_SETTINGS)
            }
        }
    }

    fun onPermissionSettingsResult(result: PermissionStatus) {
        when (result) {
            PermissionStatus.GRANTED -> {
                showDoalog(null)
                binder?.startTimer(boilingTime, eggType)
                _state.update { it.copy(buttonState = ActionButtonState.STOP) }
            }

            PermissionStatus.DENIED,
            PermissionStatus.DONT_ASK_AGAIN -> {
                showDoalog(null)
                // think about this case
            }
        }
    }

    fun onRationaleDialogConfirm() {
        showDoalog(null)
        viewModelScope.launch {
            _events.emit(BoilProgressUiEvent.RequestNotificationsPermission)
        }
    }

    fun onGoToSettingsDialogConfirm() {
        showDoalog(null)
        viewModelScope.launch {
            _events.emit(BoilProgressUiEvent.OpenNotificationsSettings)
        }
    }

    private fun getInitialState(): BoilProgressUiState {
        return BoilProgressUiState(
            boilingTime = convertMsToTimerText(boilingTime),
            titleResId = when (eggType) {
                EggBoilingType.SOFT -> R.string.common_soft_boiled_eggs
                EggBoilingType.MEDIUM -> R.string.common_medium_boiled_eggs
                EggBoilingType.HARD -> R.string.common_hard_boiled_eggs
            }
        )
    }

    private fun collectServiceState(): Job? {
        return binder?.state
            ?.onEach { timerState ->
                when (timerState) {
                    TimerStatusUpdate.Canceled -> onTimerCanceled()
                    TimerStatusUpdate.Finished -> onTimerFinished()
                    is TimerStatusUpdate.Progress -> onTimerProgressUpdate(timerState)
                }
            }?.launchIn(viewModelScope)
    }

    private fun onTimerCanceled() {
        _state.update {
            it.copy(
                progress = 0f,
                progressText = convertMsToTimerText(0L),
                buttonState = ActionButtonState.START
            )
        }
    }

    private fun onTimerFinished() {
        _state.update {
            it.copy(
                progress = 0f,
                progressText = convertMsToTimerText(0L),
                buttonState = ActionButtonState.START,
                finishCelebrationConfig = getCelebrationConfig()
            )
        }
        vibrationManager.vibratePattern(
            pattern = TIMER_FINISH_VIBRARTION_PATTERN
        )
    }

    private fun onTimerProgressUpdate(timerState: TimerStatusUpdate.Progress) {
        _state.update {
            it.copy(
                progress = timerState.valueMs / boilingTime.toFloat(),
                progressText = convertMsToTimerText(timerState.valueMs),
            )
        }
    }

    private fun showDoalog(dialog: BoilProgressUiState.Dialog?) {
        _state.update {
            it.copy(selectedDialog = dialog)
        }
    }

    private fun onStartClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            viewModelScope.launch {
                _events.emit(BoilProgressUiEvent.RequestNotificationsPermission)
            }
        } else {
            onPermissionResult(PermissionStatus.GRANTED)
        }
    }

    private fun onStopClicked() {
        binder?.stopTimer()
        _state.update { it.copy(buttonState = ActionButtonState.START) }
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
            emitter = Emitter(TIMER_FINISH_ANIMATION_DURATION_MS).perSecond(50),
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

    private companion object {
        val TIMER_FINISH_VIBRARTION_PATTERN = longArrayOf(0, 200, 100, 300, 400, 500)
        val TIMER_FINISH_ANIMATION_DURATION_MS = 3000L
    }
}
