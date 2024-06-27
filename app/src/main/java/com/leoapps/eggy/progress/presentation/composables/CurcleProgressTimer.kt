package com.leoapps.eggy.progress.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.leoapps.eggy.base.presentation.GrayLight
import com.leoapps.eggy.base.presentation.Primary
import com.leoapps.eggy.base.presentation.PrimaryAlmostWhite
import com.leoapps.eggy.base.presentation.White
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val SMALL_TO_BIG_RADIUS_RATIO = 0.75f

@Composable
fun CircleProgress(
    progress: Float,
    timerText: String,
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
                val angle = 2f * PI * progress
                val x = sin(angle) * radius
                val y = cos(angle) * radius
                val newCenter = Offset(
                    x = size.center.x + x.toFloat(),
                    y = size.center.y - y.toFloat(),
                )

                val textMeasureResult = textMeasurer.measure(
                    text = timerText,
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
                                (progress - 0.2f) to Primary,
                                center = size.center
                            ),
                            topLeft = topLeftOffset,
                            size = circleSize,
                            startAngle = 0f,
                            sweepAngle = 360f * progress,
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