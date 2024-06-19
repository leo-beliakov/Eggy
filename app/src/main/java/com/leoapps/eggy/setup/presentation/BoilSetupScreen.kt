package com.leoapps.eggy.welcome.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoapps.eggy.R
import com.leoapps.eggy.base.presentation.EggyTheme
import com.leoapps.eggy.base.presentation.GrayLight
import com.leoapps.eggy.base.presentation.Primary
import com.leoapps.eggy.base.presentation.dimens
import com.leoapps.eggy.base.presentation.utils.annotatedStringResource
import com.leoapps.eggy.base.presentation.utils.toPx
import com.leoapps.eggy.setup.presentation.composables.IconedSelectionButton
import com.leoapps.eggy.setup.presentation.composables.SelectionButton
import kotlinx.serialization.Serializable

@Serializable
object BoilSetupScreenDestination

@Composable
fun BoilSetupScreen(onContinueClicked: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceXL),
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(
                vertical = MaterialTheme.dimens.screenPaddingXL,
                horizontal = MaterialTheme.dimens.screenPaddingL,
            )
    ) {
        HeaderSection()
        TemperatureSection()
        SizeSection()
        BoiledTypeSection()
        Spacer(modifier = Modifier.weight(1f, true))
        TimerSection()
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
private fun TemperatureSection() {
    Column {
        Text(
            text = annotatedStringResource(R.string.settings_temp_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceS)
        ) {
            SelectionButton(
                titleResId = R.string.settings_temp_room_title,
                subtitleResId = R.string.settings_temp_subtitle,
                selected = false,
                onClick = { },
                modifier = Modifier.weight(1f)
            )
            SelectionButton(
                titleResId = R.string.settings_temp_fridge_title,
                subtitleResId = R.string.settings_temp_subtitle,
                selected = true,
                onClick = { },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SizeSection() {
    Column {
        Text(
            text = annotatedStringResource(R.string.settings_size_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceS)
        ) {
            SelectionButton(
                titleResId = R.string.settings_size_s,
                selected = false,
                onClick = { },
                modifier = Modifier.weight(1f)
            )
            SelectionButton(
                titleResId = R.string.settings_size_s,
                selected = true,
                onClick = { },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BoiledTypeSection() {
    Column {
        Text(
            text = annotatedStringResource(R.string.settings_type_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceXS)
        ) {
            IconedSelectionButton(
                iconResId = R.drawable.egg_soft,
                titleResId = R.string.settings_type_soft_title,
                subtitleResId = R.string.settings_type_subtitle,
                selected = false,
                onClick = { },
                modifier = Modifier.weight(1f)
            )
            IconedSelectionButton(
                iconResId = R.drawable.egg_medium,
                titleResId = R.string.settings_type_medium_title,
                subtitleResId = R.string.settings_type_subtitle,
                selected = false,
                onClick = { },
                modifier = Modifier.weight(1f)
            )
            IconedSelectionButton(
                iconResId = R.drawable.egg_hard,
                titleResId = R.string.settings_type_hard_title,
                subtitleResId = R.string.settings_type_subtitle,
                selected = true,
                onClick = { },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TimerSection() {
    Row {
        Column(
            modifier = Modifier.weight(1f, true)
        ) {
            Text(
                text = "Prepare eggs as you like!",
                style = MaterialTheme.typography.titleSmall,
                color = GrayLight,
            )
            Text(
                text = "04:50",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        ElevatedButton(
            onClick = { },
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerM),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = MaterialTheme.dimens.elevationM,
                pressedElevation = MaterialTheme.dimens.elevationL,
            ),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            contentPadding = PaddingValues(),
            modifier = Modifier.size(MaterialTheme.dimens.buttonHeight)
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
            onContinueClicked = {}
        )
    }
}
