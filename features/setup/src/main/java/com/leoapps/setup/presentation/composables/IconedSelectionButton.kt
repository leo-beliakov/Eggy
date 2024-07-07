package com.leoapps.setup.presentation.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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
fun IconedSelectionButton(
    @DrawableRes iconResId: Int,
    @StringRes titleResId: Int,
    @StringRes subtitleResId: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vibratorManager = LocalVibrationManager.current
    val selectionColor = remember(selected) { if (selected) Primary else GrayLight }

    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.spaceL)
                .fillMaxWidth()
                .sizeIn(minHeight = MaterialTheme.dimens.selectableIconedButtonHeight)
                .border(
                    width = 1.dp,
                    color = selectionColor,
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerS)
                )
                .clip(RoundedCornerShape(MaterialTheme.dimens.cornerS))
                .clickable(
                    onClick = {
                        vibratorManager.vibrateOnClick()
                        onClick()
                    },
                    indication = rememberRipple(color = PrimaryLight),
                    interactionSource = remember { MutableInteractionSource() },
                )
                .padding(MaterialTheme.dimens.paddingL)
        ) {
            Text(
                text = stringResource(id = titleResId),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = selectionColor
            )
            Text(
                text = stringResource(id = subtitleResId),
                style = MaterialTheme.typography.bodyLarge,
                color = selectionColor
            )
        }
        Image(
            painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(0.6f)
        )
    }
}

@Preview
@Composable
private fun IconedSelectionButtonPreview() {
    EggyTheme {
        IconedSelectionButton(
            iconResId = R.drawable.egg_soft,
            titleResId = R.string.settings_size_l,
            subtitleResId = R.string.settings_size_l,
            selected = false,
            onClick = { }
        )
    }
}