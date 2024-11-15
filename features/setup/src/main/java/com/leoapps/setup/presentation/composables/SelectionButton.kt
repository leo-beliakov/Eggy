package com.leoapps.setup.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.base_ui.theme.GrayLight
import com.leoapps.base_ui.theme.Primary
import com.leoapps.base_ui.theme.PrimaryLight
import com.leoapps.base_ui.theme.dimens
import com.leoapps.shared_res.R
import com.leoapps.vibration.presentation.LocalVibrationManager

@Composable
fun SelectionButton(
    @StringRes titleResId: Int,
    @StringRes subtitleResId: Int? = null,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vibratorManager = LocalVibrationManager.current
    val selectionColor = remember(selected) {
        if (selected) Primary else GrayLight
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .sizeIn(minHeight = MaterialTheme.dimens.selectableButtonHeight)
            .clip(RoundedCornerShape(MaterialTheme.dimens.cornerS))
            .border(
                width = 1.dp,
                color = selectionColor,
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerS)
            )
            .toggleable(
                value = selected,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = PrimaryLight),
                onValueChange = {
                    vibratorManager.vibrateOnClick()
                    onClick()
                }
            )
            .padding(MaterialTheme.dimens.paddingL)
    ) {
        Text(
            text = stringResource(id = titleResId),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = selectionColor
        )
        subtitleResId?.let {
            Text(
                text = stringResource(id = subtitleResId),
                style = MaterialTheme.typography.bodyLarge,
                color = selectionColor
            )
        }
    }
}

@Preview
@Composable
private fun SelectionButtonPreview() {
    EggyTheme {
        SelectionButton(
            titleResId = R.string.settings_size_l,
            subtitleResId = R.string.settings_size_l,
            selected = false,
            onClick = { }
        )
    }
}