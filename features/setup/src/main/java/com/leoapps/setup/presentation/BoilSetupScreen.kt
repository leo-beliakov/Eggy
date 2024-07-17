package com.leoapps.eggy.welcome.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.base_ui.theme.GrayLight
import com.leoapps.base_ui.theme.Primary
import com.leoapps.base_ui.theme.dimens
import com.leoapps.base_ui.utils.CollectEventsWithLifecycle
import com.leoapps.base_ui.utils.annotatedStringResource
import com.leoapps.base_ui.utils.toPx
import com.leoapps.setup.presentation.BoilSetupViewModel
import com.leoapps.setup.presentation.composables.CounterComposable
import com.leoapps.setup.presentation.composables.IconedSelectionButton
import com.leoapps.setup.presentation.composables.SelectionButton
import com.leoapps.setup.presentation.model.BoilSetupUiEvent
import com.leoapps.setup.presentation.model.BoilSetupUiState
import com.leoapps.setup.presentation.model.EggBoilingTypeUi
import com.leoapps.setup.presentation.model.EggSizeUi
import com.leoapps.setup.presentation.model.EggTemperatureUi
import com.leoapps.shared_res.R
import com.leoapps.vibration.presentation.LocalVibrationManager
import kotlinx.serialization.Serializable

@Serializable
object BoilSetupScreenDestination

@Composable
fun BoilSetupScreen(
    viewModel: BoilSetupViewModel = hiltViewModel(),
    onContinueClicked: (type: EggBoilingType, time: Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BoilSetupScreen(
        state = state,
        onNextClicked = viewModel::onNextClicked,
        onSizeSelected = viewModel::onSizeSelected,
        onTypeSelected = viewModel::onTypeSelected,
        onTemperatureSelected = viewModel::onTemperatureSelected,
    )

    CollectEventsWithLifecycle(viewModel.events) { event ->
        when (event) {
            is BoilSetupUiEvent.OpenProgressScreen -> onContinueClicked(
                event.eggType,
                event.calculatedTime
            )
        }
    }
}

@Composable
fun BoilSetupScreen(
    state: BoilSetupUiState,
    onNextClicked: () -> Unit,
    onSizeSelected: (EggSizeUi) -> Unit,
    onTypeSelected: (EggBoilingTypeUi) -> Unit,
    onTemperatureSelected: (EggTemperatureUi) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceXL),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(
                vertical = MaterialTheme.dimens.screenPaddingXL,
                horizontal = MaterialTheme.dimens.screenPaddingL,
            )
    ) {
        HeaderSection()
        TemperatureSection(
            temperatures = state.availableTemperatures,
            selectedTemperature = state.selectedTemperature,
            onTemperatureSelected = onTemperatureSelected,
        )
        SizeSection(
            sizes = state.availableSizes,
            selectedSize = state.selectedSize,
            onSizeSelected = onSizeSelected,
        )
        BoiledTypeSection(
            types = state.availableTypes,
            selectedType = state.selectedType,
            onTypeSelected = onTypeSelected,
        )
        Spacer(
            modifier = Modifier.weight(1f, true)
        )
        TimerSection(
            calculatedTime = state.calculatedTimeText,
            nextButtonEnabled = state.nextButtonEnabled,
            onNextClicked = onNextClicked,
        )
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceXS),
            modifier = Modifier
                .weight(1f, true)
                .padding(top = MaterialTheme.dimens.spaceM)
        ) {
            Text(
                text = annotatedStringResource(R.string.setup_header_title),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = stringResource(R.string.setup_header_subtitle),
                style = MaterialTheme.typography.titleSmall,
                color = GrayLight,
            )
        }
        Image(
            painter = painterResource(R.drawable.setup_egg_half),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(translationX = MaterialTheme.dimens.screenPaddingL.toPx())
                .height(120.dp)
        )
    }
}

@Composable
private fun TemperatureSection(
    temperatures: List<EggTemperatureUi>,
    selectedTemperature: EggTemperatureUi?,
    onTemperatureSelected: (EggTemperatureUi) -> Unit,
) {
    Column {
        Text(
            text = annotatedStringResource(R.string.settings_temp_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceS)
        ) {
            temperatures.forEach { temperature ->
                SelectionButton(
                    titleResId = temperature.titleResId,
                    subtitleResId = R.string.settings_temp_subtitle,
                    selected = temperature == selectedTemperature,
                    onClick = { onTemperatureSelected(temperature) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SizeSection(
    sizes: List<EggSizeUi>,
    selectedSize: EggSizeUi?,
    onSizeSelected: (EggSizeUi) -> Unit
) {
    Column {
        Text(
            text = annotatedStringResource(R.string.settings_size_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceS)
        ) {
            sizes.forEach { size ->
                SelectionButton(
                    titleResId = size.titleResId,
                    selected = size == selectedSize,
                    onClick = { onSizeSelected(size) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BoiledTypeSection(
    types: List<EggBoilingTypeUi>,
    selectedType: EggBoilingTypeUi?,
    onTypeSelected: (EggBoilingTypeUi) -> Unit
) {
    Column {
        Text(
            text = annotatedStringResource(R.string.settings_type_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceXS)
        ) {
            types.forEach { type ->
                IconedSelectionButton(
                    iconResId = type.iconResId,
                    titleResId = type.titleResId,
                    subtitleResId = R.string.settings_type_subtitle,
                    selected = type == selectedType,
                    onClick = { onTypeSelected(type) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TimerSection(
    calculatedTime: String,
    nextButtonEnabled: Boolean,
    onNextClicked: () -> Unit
) {
    val vibratorManager = LocalVibrationManager.current

    Row {
        Column(
            modifier = Modifier.weight(1f, true)
        ) {
            Text(
                text = stringResource(R.string.setup_timer_title),
                style = MaterialTheme.typography.titleSmall,
                color = GrayLight,
            )
            CounterComposable(
                currentTimeText = calculatedTime
            )
        }
        ElevatedButton(
            enabled = nextButtonEnabled,
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerM),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            onClick = {
                vibratorManager.vibrateOnClick()
                onNextClicked()
            },
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = MaterialTheme.dimens.elevationM,
                pressedElevation = MaterialTheme.dimens.elevationL,
            ),
            modifier = Modifier
                .testTag("buttonContinue")
                .size(MaterialTheme.dimens.buttonHeight)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_next),
                contentDescription = stringResource(R.string.common_continue)
            )
        }
    }
}

@Preview
@Composable
private fun BoilSetupScreenPreview() {
    EggyTheme {
        BoilSetupScreen(
            onContinueClicked = { _, _ -> }
        )
    }
}
