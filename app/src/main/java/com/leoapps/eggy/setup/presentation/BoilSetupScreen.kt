package com.leoapps.eggy.welcome.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(
                vertical = 24.dp,
                horizontal = 24.dp,
            )
    ) {
        HeaderSection()
        TemperatureSection()
        SizeSection()
        BoiledTypeSection()
        Spacer(
            modifier = Modifier.weight(1f, true)
        )
        TimerSection()
    }
}

@Composable
private fun HeaderSection() {
    Row {
        Column(
            modifier = Modifier.weight(1f, true)
        ) {
            Text(
                text = annotatedStringResource(R.string.setup_header_title),
                style = MaterialTheme.typography.titleLarge
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
            modifier = Modifier.graphicsLayer(translationX = 24.dp.toPx())
        )
    }
}

@Composable
private fun TemperatureSection() {
    Text(
        text = annotatedStringResource(R.string.settings_temp_title),
        style = MaterialTheme.typography.titleMedium,
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
}

@Composable
private fun SizeSection() {
    Text(
        text = annotatedStringResource(R.string.settings_size_title),
        style = MaterialTheme.typography.titleMedium,
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
}

@Composable
private fun BoiledTypeSection() {
    Text(
        text = annotatedStringResource(R.string.settings_type_title),
        style = MaterialTheme.typography.titleMedium,
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
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp,
            ),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            contentPadding = PaddingValues(),
            modifier = Modifier.size(48.dp)
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
