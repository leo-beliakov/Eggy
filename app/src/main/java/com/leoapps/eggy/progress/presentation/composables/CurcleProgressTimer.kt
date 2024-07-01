package com.leoapps.eggy.progress.presentation.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.toIntSize
import com.leoapps.eggy.R
import com.leoapps.eggy.base.common.theme.GrayLight
import com.leoapps.eggy.base.common.theme.Primary
import com.leoapps.eggy.base.common.theme.PrimaryAlmostWhite
import com.leoapps.eggy.base.common.theme.White
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val SMALL_TO_BIG_RADIUS_RATIO = 0.75f

class TimerState(initial: Float) {

    var progressText: String by mutableStateOf("")
    var progress: Float by mutableFloatStateOf(initial)
        private set

    suspend fun setProgress(progress: Float) {
        animate(
            initialValue = this.progress,
            targetValue = progress,
            initialVelocity = 0f,
            animationSpec = spring(
                stiffness = Spring.StiffnessVeryLow,
            ),
        ) { animatedValue, velocity ->
            this.progress = animatedValue
        }
    }

    companion object {
        val Saver: Saver<TimerState, *> = Saver(
            save = { it.progress },
            restore = { TimerState(it) }
        )
    }
}

@Composable
fun rememberTimerState(initial: Float = 0f): TimerState {
    return rememberSaveable(saver = TimerState.Saver) {
        TimerState(initial = initial)
    }
}

@Composable
fun CircleTimer(
    state: TimerState = rememberTimerState(),
    modifier: Modifier = Modifier,
) {
    val bitmapBackground = ImageBitmap.imageResource(id = R.drawable.timer_background)
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.headlineLarge

    Spacer(
        modifier = modifier
            .aspectRatio(1f)
            .drawWithCache {
                val strokeWidth = size.width / 25
                val radius = (size.width * SMALL_TO_BIG_RADIUS_RATIO - strokeWidth) / 2
                val circleSize = Size(radius * 2, radius * 2)
                val topLeftOffset = Offset(
                    (size.width * (1 - SMALL_TO_BIG_RADIUS_RATIO) + strokeWidth) / 2,
                    (size.height * (1 - SMALL_TO_BIG_RADIUS_RATIO) + strokeWidth) / 2
                )

                val indicatorRadiusBig = strokeWidth * 1.5f
                val indicatorRadiusSmall = indicatorRadiusBig * 0.75f
                val angle = 2f * PI * state.progress
                val x = sin(angle) * radius
                val y = cos(angle) * radius
                val newCenter = Offset(
                    x = size.center.x + x.toFloat(),
                    y = size.center.y - y.toFloat(),
                )

                val textMeasureResult = textMeasurer.measure(
                    text = state.progressText,
                    style = textStyle,
                )
                val textOffset = topLeftOffset + Offset(
                    x = radius - textMeasureResult.size.width / 2,
                    y = (radius - textMeasureResult.size.height) / 2,
                )

                onDrawWithContent {
                    // Draw a bitmap background
                    drawImage(
                        image = bitmapBackground,
                        dstSize = size.toIntSize(),
                    )

                    // Draw the background circle
                    drawCircle(
                        color = GrayLight,
                        radius = radius,
                        center = size.center,
                        style = Stroke(
                            width = strokeWidth
                        )
                    )

                    // Draw progress arc
                    rotate(-90f, size.center) {
                        drawArc(
                            brush = Brush.sweepGradient(
                                0f to PrimaryAlmostWhite,
                                (state.progress - 0.2f) to Primary,
                                center = size.center
                            ),
                            topLeft = topLeftOffset,
                            size = circleSize,
                            startAngle = 0f,
                            sweepAngle = 360f * state.progress,
                            useCenter = false,
                            style = Stroke(
                                width = strokeWidth
                            )
                        )
                    }

                    // Draw the decoration circle at the end of the progress
                    drawCircle(
                        color = Primary,
                        radius = indicatorRadiusBig,
                        center = newCenter,
                        style = Fill
                    )
                    drawCircle(
                        color = White,
                        radius = indicatorRadiusSmall,
                        center = newCenter,
                        style = Fill
                    )

                    // Draw timer text
                    drawText(
                        textLayoutResult = textMeasureResult,
                        topLeft = textOffset,
                    )

                }
            }
    )
}