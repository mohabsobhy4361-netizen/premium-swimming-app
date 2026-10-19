package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AquaCyan
import com.example.ui.theme.DeepAqua
import com.example.ui.theme.MidnightNavy
import kotlin.math.sin

@Composable
fun AnimatedWaterWaves(
    modifier: Modifier = Modifier,
    height: Dp = 100.dp,
    primaryColor: Color = AquaCyan.copy(alpha = 0.25f),
    secondaryColor: Color = DeepAqua.copy(alpha = 0.35f),
    tertiaryColor: Color = MidnightNavy
) {
    val infiniteTransition = rememberInfiniteTransition(label = "water_waves")

    val wavePhase1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase_1"
    )

    val wavePhase2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase_2"
    )

    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_offset"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val width = size.width
        val canvasHeight = size.height
        val waveAmplitude = canvasHeight * 0.25f
        val baseWaterLine = canvasHeight * 0.55f

        // Back wave (secondary color)
        val pathBack = Path().apply {
            moveTo(0f, canvasHeight)
            lineTo(0f, baseWaterLine)
            val step = 15f
            var x = 0f
            while (x <= width + step) {
                val y = baseWaterLine + (waveAmplitude * 0.8f) * sin((x / width * 3.5f * Math.PI.toFloat()) + wavePhase2)
                lineTo(x, y)
                x += step
            }
            lineTo(width, canvasHeight)
            close()
        }

        drawPath(
            path = pathBack,
            brush = Brush.verticalGradient(
                colors = listOf(secondaryColor, DeepAqua.copy(alpha = 0.6f), tertiaryColor),
                startY = 0f,
                endY = canvasHeight
            )
        )

        // Front wave (primary color with glowing crest)
        val pathFront = Path().apply {
            moveTo(0f, canvasHeight)
            lineTo(0f, baseWaterLine)
            val step = 12f
            var x = 0f
            while (x <= width + step) {
                val y = baseWaterLine + waveAmplitude * sin((x / width * 2.5f * Math.PI.toFloat()) + wavePhase1)
                lineTo(x, y)
                x += step
            }
            lineTo(width, canvasHeight)
            close()
        }

        drawPath(
            path = pathFront,
            brush = Brush.verticalGradient(
                colors = listOf(
                    primaryColor.copy(alpha = 0.45f + (shimmerOffset * 0.15f)),
                    DeepAqua.copy(alpha = 0.7f),
                    tertiaryColor
                ),
                startY = baseWaterLine - waveAmplitude,
                endY = canvasHeight
            )
        )

        // Subtle glowing crest line
        val crestPath = Path().apply {
            moveTo(0f, baseWaterLine + waveAmplitude * sin(wavePhase1))
            val step = 15f
            var x = 0f
            while (x <= width + step) {
                val y = baseWaterLine + waveAmplitude * sin((x / width * 2.5f * Math.PI.toFloat()) + wavePhase1)
                lineTo(x, y)
                x += step
            }
        }

        drawPath(
            path = crestPath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    AquaCyan.copy(alpha = 0.2f),
                    AquaCyan.copy(alpha = 0.8f),
                    AquaCyan.copy(alpha = 0.2f)
                )
            ),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.5f)
        )
    }
}
