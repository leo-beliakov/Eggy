package com.leoapps.eggy.setup.presentation.composables

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoapps.eggy.R
import com.leoapps.eggy.base.presentation.EggyTheme
import com.leoapps.eggy.base.presentation.GrayLight
import com.leoapps.eggy.base.presentation.Primary
import com.leoapps.eggy.base.presentation.PrimaryLight

@Composable
fun IconedSelectionButton(
    @DrawableRes iconResId: Int,
    @StringRes titleResId: Int,
    @StringRes subtitleResId: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectionColor = remember(selected) { if (selected) Primary else GrayLight }

    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .sizeIn(minHeight = 150.dp)
                .border(
                    width = 1.dp,
                    color = selectionColor,
                    shape = RoundedCornerShape(6.dp)
                )
                .clickable(
                    onClick = onClick,
                    indication = rememberRipple(color = PrimaryLight),
                    interactionSource = remember { MutableInteractionSource() },
                )
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = titleResId),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                color = selectionColor
            )
            Text(
                text = stringResource(id = subtitleResId),
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