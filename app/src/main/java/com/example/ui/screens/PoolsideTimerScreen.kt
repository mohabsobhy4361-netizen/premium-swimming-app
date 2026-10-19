package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnimatedWaterWaves
import com.example.ui.components.LuxuryBadge
import com.example.ui.theme.AquaCyan
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoralRed
import com.example.ui.theme.DeepAbyss
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

data class LapRecord(
    val lapIndex: Int,
    val splitTimeMs: Long,
    val lapDurationMs: Long,
    val distanceM: Int = 50
)

@Composable
fun PoolsideTimerScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTimeMs by remember { mutableLongStateOf(0L) }
    val laps = remember { mutableStateListOf<LapRecord>() }
    var previousLapTotalMs by remember { mutableLongStateOf(0L) }

    // Stroke Rate (SPM) Tap Meter state
    val strokeTapTimestamps = remember { mutableStateListOf<Long>() }
    var calculatedSpm by remember { mutableStateOf<Int?>(null) }

    val vibrator = remember {
        try {
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        } catch (_: Exception) {
            null
        }
    }

    fun triggerVibe(durationMs: Long = 30) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (_: Exception) {}
    }

    // Timer tick loop
    LaunchedEffect(isRunning) {
        if (isRunning) {
            val startTime = System.currentTimeMillis() - elapsedTimeMs
            while (isRunning) {
                elapsedTimeMs = System.currentTimeMillis() - startTime
                delay(16) // ~60fps smooth timer update
            }
        }
    }

    // Pulse animation for running state
    val pulseTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isRunning) 1.08f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(SurfaceNavy, MidnightNavy)))
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "POOLSIDE CHRONO",
                            style = MaterialTheme.typography.titleLarge,
                            color = AquaCyan,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "Olympic 50m / 25m Precision Telemetry",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                    LuxuryBadge(text = "FINA Precision", isGold = true, icon = Icons.Default.Speed)
                }
            }
        }

        AnimatedWaterWaves(height = 36.dp)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Digital Stopwatch Display
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            1.5.dp,
                            if (isRunning) AquaCyan.copy(alpha = 0.8f) else BorderSubtle,
                            RoundedCornerShape(24.dp)
                        ),
                    color = DeepAbyss
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val minutes = (elapsedTimeMs / 60000)
                        val seconds = (elapsedTimeMs % 60000) / 1000
                        val hundredths = (elapsedTimeMs % 1000) / 10

                        Text(
                            text = String.format("%02d:%02d.%02d", minutes, seconds, hundredths),
                            fontSize = 54.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = if (isRunning) AquaCyan else TextPrimary,
                            letterSpacing = 2.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        val pacePer100m = if (elapsedTimeMs > 0 && laps.isNotEmpty()) {
                            val totalDistM = laps.size * 50
                            val paceSec = (elapsedTimeMs / 1000.0) / (totalDistM / 100.0)
                            String.format("Avg Pace: %02d:%02d / 100m", (paceSec / 60).toInt(), (paceSec % 60).toInt())
                        } else {
                            "Pace Target: 01:12 / 100m"
                        }

                        Text(
                            text = pacePer100m,
                            color = ChampagneGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Controls Row
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Reset Button
                            OutlinedButton(
                                onClick = {
                                    triggerVibe(40)
                                    isRunning = false
                                    elapsedTimeMs = 0L
                                    previousLapTotalMs = 0L
                                    laps.clear()
                                },
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(54.dp)
                                    .testTag("timer_reset_btn"),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Reset")
                            }

                            // Start / Stop Main Action
                            Box(
                                modifier = Modifier
                                    .scale(if (isRunning) pulseScale else 1f)
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isRunning) CoralRed else AquaCyan
                                    )
                                    .clickable {
                                        triggerVibe(60)
                                        isRunning = !isRunning
                                    }
                                    .testTag("timer_toggle_btn"),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isRunning) "Pause" else "Start",
                                    tint = MidnightNavy,
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            // Lap / Split Button
                            Button(
                                onClick = {
                                    if (isRunning) {
                                        triggerVibe(40)
                                        val lapDuration = elapsedTimeMs - previousLapTotalMs
                                        previousLapTotalMs = elapsedTimeMs
                                        laps.add(0, LapRecord(laps.size + 1, elapsedTimeMs, lapDuration))
                                    }
                                },
                                shape = CircleShape,
                                enabled = isRunning,
                                modifier = Modifier
                                    .size(54.dp)
                                    .testTag("timer_split_btn"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SurfaceNavy,
                                    contentColor = AquaCyan,
                                    disabledContainerColor = SurfaceNavy.copy(alpha = 0.4f),
                                    disabledContentColor = TextMuted
                                )
                            ) {
                                Icon(Icons.Default.Flag, contentDescription = "Lap Split")
                            }
                        }
                    }
                }
            }

            // Interactive Stroke Cadence (SPM) Tap Tool
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp)),
                    color = SurfaceCard
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.TouchApp,
                                    contentDescription = null,
                                    tint = AquaCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Stroke Rate Cadence (SPM)",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            if (calculatedSpm != null) {
                                LuxuryBadge(
                                    text = "$calculatedSpm SPM",
                                    isGold = (calculatedSpm ?: 0) >= 44
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Tap button 3 times in rhythm with swimmer hand entries to calculate strokes per minute.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    triggerVibe(30)
                                    val now = System.currentTimeMillis()
                                    strokeTapTimestamps.add(now)
                                    if (strokeTapTimestamps.size > 3) {
                                        strokeTapTimestamps.removeAt(0)
                                    }
                                    if (strokeTapTimestamps.size >= 3) {
                                        val totalDeltaMs = strokeTapTimestamps.last() - strokeTapTimestamps.first()
                                        val avgStrokeTimeMs = totalDeltaMs / (strokeTapTimestamps.size - 1)
                                        if (avgStrokeTimeMs > 200) {
                                            val spm = (60000.0 / avgStrokeTimeMs).toInt()
                                            calculatedSpm = spm.coerceIn(20, 80)
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AquaCyan.copy(alpha = 0.2f),
                                    contentColor = AquaCyan
                                ),
                                modifier = Modifier.testTag("spm_tap_btn")
                            ) {
                                Icon(Icons.Default.TouchApp, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Tap Stroke Entry (${strokeTapTimestamps.size}/3)")
                            }

                            if (calculatedSpm != null) {
                                Text(
                                    text = when {
                                        (calculatedSpm ?: 0) < 32 -> "Aerobic Cruise"
                                        (calculatedSpm ?: 0) in 32..42 -> "Threshold Pacing"
                                        else -> "Sprint Explosion"
                                    },
                                    color = ChampagneGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Lap Splits Table
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recorded Splits (${laps.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    if (laps.isNotEmpty()) {
                        Text(
                            text = "Clear Splits",
                            color = CoralRed,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clickable {
                                    laps.clear()
                                    previousLapTotalMs = 0L
                                }
                                .padding(4.dp)
                        )
                    }
                }
            }

            if (laps.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceNavy.copy(alpha = 0.5f))
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No laps recorded yet. Start timer and press the Lap Flag to record 50m intervals.",
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                itemsIndexed(laps) { _, lap ->
                    val lapSec = lap.lapDurationMs / 1000
                    val lapHundredths = (lap.lapDurationMs % 1000) / 10
                    val splitSec = lap.splitTimeMs / 1000
                    val splitHundredths = (lap.splitTimeMs % 1000) / 10

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp)),
                        color = DeepAbyss
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "#${lap.lapIndex}",
                                    fontWeight = FontWeight.Bold,
                                    color = ChampagneGold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Lap: ${String.format("%02d.%02d s", lapSec, lapHundredths)}",
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${lap.lapIndex * 50}m Total",
                                        color = TextMuted,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = String.format("%02d:%02d.%02d", splitSec / 60, splitSec % 60, splitHundredths),
                                    fontFamily = FontFamily.Monospace,
                                    color = AquaCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Split Time",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
