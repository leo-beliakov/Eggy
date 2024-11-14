package com.leoapps.base_ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    // Screen Paddings
    val screenPaddingS: Dp,
    val screenPaddingM: Dp,
    val screenPaddingL: Dp,
    val screenPaddingXL: Dp,

    // Spaces between Elements
    val spaceXS: Dp,
    val spaceS: Dp,
    val spaceM: Dp,
    val spaceL: Dp,
    val spaceXL: Dp,

    // Element Sizes
    val buttonMaxWidth: Dp,
    val buttonHeight: Dp,
    val selectableButtonHeight: Dp,
    val selectableIconedButtonHeight: Dp,

    // Corner Radiuses
    val cornerS: Dp,
    val cornerM: Dp,
    val cornerL: Dp,

    // Elevations
    val elevationS: Dp,
    val elevationM: Dp,
    val elevationL: Dp,

    // Icon Sizes
    val iconSizeS: Dp,
    val iconSizeM: Dp,
    val iconSizeL: Dp,
    val iconSizeXL: Dp,

    // Interactive Components Sizes
    val minimumInteractiveComponentSize: Dp,

    // Paddings
    val paddingXS: Dp,
    val paddingS: Dp,
    val paddingM: Dp,
    val paddingL: Dp
)

val compatSmallDimensions = Dimensions(
    screenPaddingS = 4.dp,
    screenPaddingM = 8.dp,
    screenPaddingL = 12.dp,
    screenPaddingXL = 16.dp,
    spaceXS = 4.dp,
    spaceS = 6.dp,
    spaceM = 8.dp,
    spaceL = 12.dp,
    spaceXL = 14.dp,
    buttonMaxWidth = 400.dp,
    buttonHeight = 56.dp,
    selectableButtonHeight = 70.dp,
    selectableIconedButtonHeight = 100.dp,
    cornerS = 4.dp,
    cornerM = 8.dp,
    cornerL = 12.dp,
    elevationS = 1.dp,
    elevationM = 2.dp,
    elevationL = 4.dp,
    iconSizeS = 12.dp,
    iconSizeM = 20.dp,
    iconSizeL = 28.dp,
    iconSizeXL = 36.dp,
    minimumInteractiveComponentSize = 40.dp,
    paddingXS = 2.dp,
    paddingS = 4.dp,
    paddingM = 6.dp,
    paddingL = 8.dp
)

val compatMediumDimensions = Dimensions(
    screenPaddingS = 8.dp,
    screenPaddingM = 16.dp,
    screenPaddingL = 24.dp,
    screenPaddingXL = 32.dp,
    spaceXS = 6.dp,
    spaceS = 8.dp,
    spaceM = 12.dp,
    spaceL = 16.dp,
    spaceXL = 18.dp,
    buttonMaxWidth = 500.dp,
    buttonHeight = 64.dp,
    selectableButtonHeight = 80.dp,
    selectableIconedButtonHeight = 120.dp,
    cornerS = 6.dp,
    cornerM = 12.dp,
    cornerL = 16.dp,
    elevationS = 2.dp,
    elevationM = 4.dp,
    elevationL = 8.dp,
    iconSizeS = 16.dp,
    iconSizeM = 24.dp,
    iconSizeL = 32.dp,
    iconSizeXL = 42.dp,
    minimumInteractiveComponentSize = 48.dp,
    paddingXS = 4.dp,
    paddingS = 8.dp,
    paddingM = 12.dp,
    paddingL = 16.dp
)

val compatLargeDimensions = Dimensions(
    screenPaddingS = 10.dp,
    screenPaddingM = 18.dp,
    screenPaddingL = 26.dp,
    screenPaddingXL = 34.dp,
    spaceXS = 7.dp,
    spaceS = 9.dp,
    spaceM = 14.dp,
    spaceL = 18.dp,
    spaceXL = 20.dp,
    buttonMaxWidth = 550.dp,
    buttonHeight = 68.dp,
    selectableButtonHeight = 85.dp,
    selectableIconedButtonHeight = 130.dp,
    cornerS = 7.dp,
    cornerM = 14.dp,
    cornerL = 18.dp,
    elevationS = 3.dp,
    elevationM = 5.dp,
    elevationL = 9.dp,
    iconSizeS = 18.dp,
    iconSizeM = 26.dp,
    iconSizeL = 34.dp,
    iconSizeXL = 44.dp,
    minimumInteractiveComponentSize = 52.dp,
    paddingXS = 5.dp,
    paddingS = 9.dp,
    paddingM = 14.dp,
    paddingL = 18.dp
)

val mediumDimensions = Dimensions(
    screenPaddingS = 12.dp,
    screenPaddingM = 20.dp,
    screenPaddingL = 28.dp,
    screenPaddingXL = 36.dp,
    spaceXS = 8.dp,
    spaceS = 10.dp,
    spaceM = 16.dp,
    spaceL = 20.dp,
    spaceXL = 22.dp,
    buttonMaxWidth = 600.dp,
    buttonHeight = 72.dp,
    selectableButtonHeight = 90.dp,
    selectableIconedButtonHeight = 140.dp,
    cornerS = 8.dp,
    cornerM = 16.dp,
    cornerL = 20.dp,
    elevationS = 3.dp,
    elevationM = 6.dp,
    elevationL = 10.dp,
    iconSizeS = 20.dp,
    iconSizeM = 28.dp,
    iconSizeL = 36.dp,
    iconSizeXL = 48.dp,
    minimumInteractiveComponentSize = 56.dp,
    paddingXS = 6.dp,
    paddingS = 10.dp,
    paddingM = 14.dp,
    paddingL = 20.dp
)

val expandedDimensions = Dimensions(
    screenPaddingS = 16.dp,
    screenPaddingM = 24.dp,
    screenPaddingL = 32.dp,
    screenPaddingXL = 40.dp,
    spaceXS = 10.dp,
    spaceS = 12.dp,
    spaceM = 20.dp,
    spaceL = 24.dp,
    spaceXL = 26.dp,
    buttonMaxWidth = 700.dp,
    buttonHeight = 80.dp,
    selectableButtonHeight = 100.dp,
    selectableIconedButtonHeight = 160.dp,
    cornerS = 10.dp,
    cornerM = 20.dp,
    cornerL = 24.dp,
    elevationS = 4.dp,
    elevationM = 8.dp,
    elevationL = 12.dp,
    iconSizeS = 24.dp,
    iconSizeM = 32.dp,
    iconSizeL = 40.dp,
    iconSizeXL = 52.dp,
    minimumInteractiveComponentSize = 64.dp,
    paddingXS = 8.dp,
    paddingS = 12.dp,
    paddingM = 18.dp,
    paddingL = 24.dp
)

internal val LocalDimens = staticCompositionLocalOf { mediumDimensions }

val MaterialTheme.dimens: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

// Function to get dimensions based on screen width
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WithDimensionsBasedOnScreenSize(
    content: @Composable () -> Unit
) {
    val activity = LocalContext.current as Activity
    val windowSizeClass = calculateWindowSizeClass(activity)
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val dimensions = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> when {
            screenWidthDp <= 360 -> compatSmallDimensions
            screenWidthDp <= 600 -> compatMediumDimensions
            else -> compatLargeDimensions
        }

        WindowWidthSizeClass.Medium -> mediumDimensions
        WindowWidthSizeClass.Expanded -> expandedDimensions
        else -> compatMediumDimensions
    }

    CompositionLocalProvider(LocalDimens provides dimensions) {
        content()
    }
}
