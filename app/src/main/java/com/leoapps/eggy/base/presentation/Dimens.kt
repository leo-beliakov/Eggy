package com.leoapps.eggy.base.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    // Screen Paddings
    val screenPaddingS: Dp = 8.dp,
    val screenPaddingM: Dp = 16.dp,
    val screenPaddingL: Dp = 24.dp,

    // Element Gaps
    val elementGapS: Dp = 4.dp,
    val elementGapM: Dp = 8.dp,
    val elementGapL: Dp = 16.dp,

    // Element Sizes
    val buttonHeight: Dp = 64.dp,
    val selectableButtonHeight: Dp = 24.dp,
    val selectableIconedButtonHeight: Dp = 48.dp,

    // Corner Radiuses
    val cornerS: Dp = 6.dp,
    val cornerM: Dp = 12.dp,
    val cornerL: Dp = 16.dp,

    // Elevations
    val elevationS: Dp = 2.dp,
    val elevationM: Dp = 4.dp,
    val elevationL: Dp = 8.dp,

    // Icon Sizes
    val smallIconSize: Dp = 16.dp,
    val mediumIconSize: Dp = 24.dp,
    val largeIconSize: Dp = 32.dp,

    // Paddings
    val smallComponentPadding: Dp = 8.dp,
    val mediumComponentPadding: Dp = 12.dp,
    val largeComponentPadding: Dp = 16.dp
)

internal val LocalDimens = staticCompositionLocalOf { Dimensions() }

val MaterialTheme.dimens: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current