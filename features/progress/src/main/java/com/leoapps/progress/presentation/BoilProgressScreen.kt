package com.leoapps.progress.presentation

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.permissions.OpenNotificationsSettingsContract
import com.leoapps.base.permissions.resolvePermissionStatus
import com.leoapps.base_ui.theme.GrayLight
import com.leoapps.base_ui.theme.GraySuperLight
import com.leoapps.base_ui.theme.Primary
import com.leoapps.base_ui.theme.White
import com.leoapps.base_ui.theme.dimens
import com.leoapps.base_ui.utils.CollectEventsWithLifecycle
import com.leoapps.base_ui.utils.CurrentActivity
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import com.leoapps.progress.presentation.composables.BoilingParameterItem
import com.leoapps.progress.presentation.composables.CancelationDialog
import com.leoapps.progress.presentation.composables.CircleTimer
import com.leoapps.progress.presentation.composables.PermissionOpenSettingsDialog
import com.leoapps.progress.presentation.composables.PermissionRationaleDialog
import com.leoapps.progress.presentation.composables.TimerState
import com.leoapps.progress.presentation.composables.rememberTimerState
import com.leoapps.progress.presentation.model.ActionButtonState
import com.leoapps.progress.presentation.model.BoilProgressUiEvent
import com.leoapps.shared_res.R
import com.leoapps.vibration.presentation.LocalVibrationManager
import kotlinx.serialization.Serializable
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.PartySystem

@Serializable
data class BoilProgressScreenDestination(
    val type: EggBoilingType,
    val calculatedTime: Long,
)

@Composable
fun BoilProgressScreen(
    viewModel: BoilProgressViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val activity = CurrentActivity()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = @RequiresApi(Build.VERSION_CODES.TIRAMISU) { granted ->
            val result = resolvePermissionStatus(
                activity = activity,
                isGranted = granted,
                permission = Manifest.permission.POST_NOTIFICATIONS
            )
            viewModel.onPermissionResult(result)
        },
    )
    val permissionSettingsLauncher = rememberLauncherForActivityResult(
        contract = OpenNotificationsSettingsContract(),
        onResult = @RequiresApi(Build.VERSION_CODES.TIRAMISU) {
            val result = resolvePermissionStatus(
                activity = activity,
                permission = Manifest.permission.POST_NOTIFICATIONS
            )
            viewModel.onPermissionSettingsResult(result)
        },
    )

    BoilProgressScreen(
        state = state,
        onBackClicked = viewModel::onBackClicked,
        onButtonClicked = viewModel::onButtonClicked,
        onCelebrationFinished = viewModel::onCelebrationFinished,
        onCancelationDialogConfirmed = viewModel::onCancelationDialogConfirmed,
        onRationaleDialogConfirm = viewModel::onRationaleDialogConfirm,
        onGoToSettingsDialogConfirm = viewModel::onGoToSettingsDialogConfirm,
        onCancelationDialogDismissed = viewModel::onCancelationDialogDismissed,
    )

    CollectEventsWithLifecycle(viewModel.events) { event ->
        when (event) {
            is BoilProgressUiEvent.NavigateBack -> onBackClicked()

            is BoilProgressUiEvent.RequestNotificationsPermission -> {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

            is BoilProgressUiEvent.OpenNotificationsSettings -> {
                permissionSettingsLauncher.launch()
            }
        }
    }
}

@Composable
fun BoilProgressScreen(
    state: BoilProgressUiState,
    onBackClicked: () -> Unit,
    onButtonClicked: () -> Unit,
    onCelebrationFinished: () -> Unit,
    onCancelationDialogConfirmed: () -> Unit,
    onRationaleDialogConfirm: () -> Unit,
    onGoToSettingsDialogConfirm: () -> Unit,
    onCancelationDialogDismissed: () -> Unit,
) {
    BoilProgressContent(
        state = state,
        onBackClicked = onBackClicked,
        onButtonClicked = onButtonClicked,
        onCelebrationFinished = onCelebrationFinished,
    )

    state.selectedDialog?.let { dialog ->
        BoilProgressDialog(
            dialog = dialog,
            onCancelationConfirm = onCancelationDialogConfirmed,
            onRationaleConfirm = onRationaleDialogConfirm,
            onGoToSettingsConfirm = onGoToSettingsDialogConfirm,
            onDismiss = onCancelationDialogDismissed,
        )
    }
}

@Composable
fun BoilProgressDialog(
    dialog: BoilProgressUiState.Dialog,
    onCancelationConfirm: () -> Unit,
    onRationaleConfirm: () -> Unit,
    onGoToSettingsConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    when (dialog) {
        BoilProgressUiState.Dialog.CANCELATION -> CancelationDialog(
            onConfirm = onCancelationConfirm,
            onDismiss = onDismiss,
        )

        BoilProgressUiState.Dialog.RATIONALE -> PermissionRationaleDialog(
            onConfirm = onRationaleConfirm,
            onDismiss = onDismiss,
        )

        BoilProgressUiState.Dialog.RATIONALE_GO_TO_SETTINGS -> PermissionOpenSettingsDialog(
            onConfirm = onGoToSettingsConfirm,
            onDismiss = onDismiss,
        )
    }
}

@Composable
private fun BoilProgressContent(
    state: BoilProgressUiState,
    onBackClicked: () -> Unit,
    onButtonClicked: () -> Unit,
    onCelebrationFinished: () -> Unit
) {
    val timerState = rememberTimerState()

    LaunchedEffect(state.progress, state.progressText) {
        timerState.setProgress(state.progress)
        timerState.progressText = state.progressText
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    vertical = MaterialTheme.dimens.screenPaddingXL,
                    horizontal = MaterialTheme.dimens.screenPaddingL
                )
        ) {
            Toolbar(
                titleResId = state.titleResId,
                onBackClicked = onBackClicked,
            )
            TimerSection(
                timerState = timerState
            )
            BoilingParametersSection(
                boilingTime = state.boilingTime
            )
            TipsSection()
            Spacer(
                modifier = Modifier.weight(1f, true)
            )
            ButtonStartSection(
                buttonState = state.buttonState,
                onButtonClicked = onButtonClicked,
            )
        }
        state.finishCelebrationConfig?.let { parties ->
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = parties,
                updateListener = object : OnParticleSystemUpdateListener {
                    override fun onParticleSystemEnded(
                        system: PartySystem,
                        activeSystems: Int,
                    ) {
                        if (activeSystems == 0) onCelebrationFinished()
                    }
                }
            )
        }
    }
}

@Composable
private fun Toolbar(
    titleResId: Int,
    onBackClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(id = R.string.common_back)
            )
        }
        Text(
            text = stringResource(id = titleResId),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                // reserves the size of the icon button
                // to prevent overlapping:
                .padding(horizontal = MaterialTheme.dimens.minimumInteractiveComponentSize)
        )
    }
}

@Composable
private fun TimerSection(
    timerState: TimerState,
) {
    BoxWithConstraints {
        val timerSize = min(
            maxHeight * 0.5f,
            maxWidth * 0.8f
        )

        CircleTimer(
            state = timerState,
            modifier = Modifier
                .size(timerSize)
                .padding(top = MaterialTheme.dimens.spaceL)
        )
    }
}

@Composable
private fun BoilingParametersSection(
    boilingTime: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceS),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.dimens.spaceL)
    ) {
        BoilingParameterItem(
            painter = painterResource(id = R.drawable.ic_timer),
            title = stringResource(id = R.string.progress_boiled_time),
            value = boilingTime,
        )
        BoilingParameterItem(
            painter = painterResource(id = R.drawable.ic_thermometer),
            title = stringResource(id = R.string.progress_boiled_temperature),
            value = "100C",
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.dimens.spaceM)
                .height(1.dp)
                .background(GraySuperLight)
        )
    }
}

@Composable
private fun TipsSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceS),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = MaterialTheme.dimens.spaceXL,
                horizontal = MaterialTheme.dimens.paddingM
            )
    ) {
        Text(
            text = stringResource(id = R.string.progress_tips_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(id = R.string.progress_tip_1),
            style = MaterialTheme.typography.titleSmall,
            color = GrayLight,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ButtonStartSection(
    buttonState: ActionButtonState,
    onButtonClicked: () -> Unit,
) {
    val vibratorManager = LocalVibrationManager.current

    ElevatedButton(
        onClick = {
            vibratorManager.vibrateOnClick()
            onButtonClicked()
        },
        shape = RoundedCornerShape(MaterialTheme.dimens.cornerM),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = MaterialTheme.dimens.elevationM,
            pressedElevation = MaterialTheme.dimens.elevationL,
        ),
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        modifier = Modifier
            .widthIn(max = MaterialTheme.dimens.buttonMaxWidth)
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimens.buttonHeight)
    ) {
        Text(
            text = stringResource(id = buttonState.textResId),
            style = MaterialTheme.typography.titleMedium,
            color = White,
            fontWeight = FontWeight.W700,
        )
    }
}
