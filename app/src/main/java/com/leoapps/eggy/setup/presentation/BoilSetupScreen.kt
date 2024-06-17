package com.leoapps.eggy.splash.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoapps.eggy.R
import com.leoapps.eggy.base.presentation.EggyTheme
import com.leoapps.eggy.setup.presentation.composables.IconedSelectionButton
import com.leoapps.eggy.setup.presentation.composables.SelectionButton
import kotlinx.serialization.Serializable


@Serializable
object BoilSetupScreenDestination

@Composable
fun BoilSetupScreen(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(
                vertical = 24.dp,
                horizontal = 24.dp,
            )
    ) {
        Text(
            text = stringResource(id = R.string.settings_temp_title)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
        Text(
            text = stringResource(id = R.string.settings_size_title)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
        Text(
            text = stringResource(id = R.string.settings_type_title)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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

@Preview
@Composable
private fun BoilSetupScreenPreview() {
    EggyTheme {
        BoilSetupScreen(
            onContinueClicked = {}
        )
    }
}