package com.leoapps.eggy.progress.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leoapps.eggy.R
import com.leoapps.eggy.base.common.theme.GrayLight
import com.leoapps.eggy.base.common.theme.GraySuperLight
import com.leoapps.eggy.base.common.theme.Primary
import com.leoapps.eggy.base.common.theme.White
import com.leoapps.eggy.base.common.theme.dimens
import com.leoapps.eggy.base.common.utils.CollectEventsWithLifecycle
import com.leoapps.eggy.base.vibration.presentation.LocalVibrationManager
import com.leoapps.eggy.progress.presentation.composables.CancelationDialog
import com.leoapps.eggy.progress.presentation.composables.CircleTimer
import com.leoapps.eggy.progress.presentation.composables.TimerState
import com.leoapps.eggy.progress.presentation.composables.rememberTimerState
import com.leoapps.eggy.progress.presentation.model.ActionButtonState
import com.leoapps.eggy.progress.presentation.model.BoilProgressUiEvent
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.PartySystem

@Serializable
data class BoilProgressScreenDestination(
    //todo: pass enum instead (https://medium.com/@edmiro/type-safety-in-navigation-compose-23c03e3d74a5)
    val type: String,
    val calculatedTime: Long,
)

@Composable
fun BoilProgressScreen(
    viewModel: BoilProgressViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val timerState = rememberTimerState()
    val coroutineScope = rememberCoroutineScope()

    BoilProgressScreen(
        state = state,
        timerState = timerState,
        onBackClicked = viewModel::onBackClicked,
        onButtonClicked = viewModel::onButtonClicked,
        onCelebrationFinished = viewModel::onCelebrationFinished,
    )

    if (state.showCancelationDialog) {
        CancelationDialog(
            onConfirm = viewModel::onCancelationDialogConfirmed,
            onDismiss = viewModel::onCancelationDialogDismissed
        )
    }

    CollectEventsWithLifecycle(viewModel.events) { event ->
        when (event) {
            is BoilProgressUiEvent.NavigateBack -> onBackClicked()
            is BoilProgressUiEvent.UpdateTimer -> coroutineScope.launch {
                timerState.progressText = event.progressText
                timerState.setProgress(event.progress)
            }
        }
    }
}

@Composable
private fun BoilProgressScreen(
    state: BoilProgressUiState,
    timerState: TimerState,
    onBackClicked: () -> Unit,
    onButtonClicked: () -> Unit,
    onCelebrationFinished: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeDrawingPadding()
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
                contentDescription = null
            )
        }
        Text(
            text = stringResource(id = titleResId),
            style = MaterialTheme.typography.titleLarge,
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
    CircleTimer(
        state = timerState,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(top = MaterialTheme.dimens.spaceL)
    )
}

@Composable
private fun BoilingParametersSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceS),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.dimens.spaceL)
    ) {
        BoilingParameterItem(
            painter = painterResource(id = R.drawable.ic_timer),
            title = stringResource(id = R.string.progress_boiled_time),
            value = "02:00",
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
private fun BoilingParameterItem(
    painter: Painter,
    title: String,
    value: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painter,
            tint = White,
            contentDescription = null,
            modifier = Modifier
                .size(MaterialTheme.dimens.iconSizeXL)
                .background(Primary, RoundedCornerShape(MaterialTheme.dimens.cornerS))
                .padding(MaterialTheme.dimens.paddingS)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = GrayLight,
            modifier = Modifier.weight(1f, true)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
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
