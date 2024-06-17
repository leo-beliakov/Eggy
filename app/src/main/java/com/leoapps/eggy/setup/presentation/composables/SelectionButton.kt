package com.leoapps.eggy.setup.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun SelectionButton(
    @StringRes titleResId: Int,
    @StringRes subtitleResId: Int? = null,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectionColor = remember(selected) {
        if (selected) Primary else GrayLight
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .sizeIn(minHeight = 100.dp)
            .border(
                width = 1.dp,
                color = selectionColor,
                shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp))
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
        subtitleResId?.let {
            Text(
                text = stringResource(id = subtitleResId),
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