package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.HotTub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BookingSession
import com.example.model.ProgramCategory
import com.example.ui.components.AnimatedWaterWaves
import com.example.ui.components.LuxuryBadge
import com.example.ui.theme.AquaCyan
import com.example.ui.theme.AquaCyanGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoralRed
import com.example.ui.theme.DarkGold
import com.example.ui.theme.DeepAbyss
import com.example.ui.theme.DeepAqua
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.util.Calendar

enum class FacilityArea(val title: String, val subtitle: String) {
    OLYMPIC_50M("50m Olympic Pool", "8 FINA Championship Competition Lanes"),
    PRIVATE_SUITES("Private Hydro Suites", "Warm Therapy, Ladies Oasis & Flume")
}

data class CalendarDay(
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val dateString: String,
    val isAvailable: Boolean = true,
    val hasPrivateSlot: Boolean = true,
    val hasTeamSprintBlock: Boolean = false
)

data class PrivateSuite(
    val id: String,
    val name: String,
    val category: String,
    val temp: String,
    val capacity: String,
    val price: Int,
    val features: List<String>
)

data class CoachOption(
    val id: String,
    val name: String,
    val specialty: String,
    val ratePerHour: Int
)

@Composable
fun FacilityBookingScreen(
    onBookingConfirmed: (BookingSession) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val vibrator = remember {
        try {
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        } catch (_: Exception) {
            null
        }
    }

    fun haptic(duration: Long = 35) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(duration)
            }
        } catch (_: Exception) {}
    }

    // Days setup for current month
    val daysList = remember {
        listOf(
            CalendarDay(19, "Mon", "Oct 19, 2026", isAvailable = true, hasPrivateSlot = true),
            CalendarDay(20, "Tue", "Oct 20, 2026", isAvailable = true, hasTeamSprintBlock = true),
            CalendarDay(21, "Wed", "Oct 21, 2026", isAvailable = true, hasPrivateSlot = true),
            CalendarDay(22, "Thu", "Oct 22, 2026", isAvailable = true, hasTeamSprintBlock = true),
            CalendarDay(23, "Fri", "Oct 23, 2026", isAvailable = true, hasPrivateSlot = true),
            CalendarDay(24, "Sat", "Oct 24, 2026", isAvailable = true, hasPrivateSlot = true, hasTeamSprintBlock = true),
            CalendarDay(25, "Sun", "Oct 25, 2026", isAvailable = true, hasPrivateSlot = true),
            CalendarDay(26, "Mon", "Oct 26, 2026", isAvailable = true),
            CalendarDay(27, "Tue", "Oct 27, 2026", isAvailable = true, hasPrivateSlot = true),
            CalendarDay(28, "Wed", "Oct 28, 2026", isAvailable = true, hasTeamSprintBlock = true),
            CalendarDay(29, "Thu", "Oct 29, 2026", isAvailable = true, hasPrivateSlot = true),
            CalendarDay(30, "Fri", "Oct 30, 2026", isAvailable = true, hasPrivateSlot = true)
        )
    }

    var selectedDay by remember { mutableStateOf(daysList[5]) } // Default to Saturday
    var selectedFacilityArea by remember { mutableStateOf(FacilityArea.OLYMPIC_50M) }
    var selectedLaneNumber by remember { mutableIntStateOf(4) } // Lane 4 (Center Fast Lane)
    var selectedSuiteId by remember { mutableStateOf("suite_a") }

    val timeSlots = listOf(
        "06:00 AM – 07:15 AM (Dawn Masters)",
        "07:30 AM – 08:45 AM (Triathlon Aerobic)",
        "09:30 AM – 10:45 AM (Baby & Ladies Sanctuary)",
        "11:00 AM – 12:15 PM (Stroke Biomechanics Clinic)",
        "04:00 PM – 05:15 PM (Junior Elite Squad)",
        "05:30 PM – 07:00 PM (Titans Senior Championship)",
        "07:30 PM – 08:45 PM (Executive Night Swim)"
    )
    var selectedTimeSlot by remember { mutableStateOf(timeSlots[2]) }

    val coaches = listOf(
        CoachOption("none", "Independent Member Swim", "Self-directed lane access", 0),
        CoachOption("viktor", "Head Coach Viktor Kazakov", "Olympic Medalist / Race Biomechanics", 65),
        CoachOption("elena", "Coach Elena Rostova", "World Cup Swimmer / Stroke Technique", 50),
        CoachOption("maya", "Dr. Maya Lindqvist, PT", "Hydrotherapy & Prenatal Aquatics", 55)
    )
    var selectedCoach by remember { mutableStateOf(coaches[0]) }

    val privateSuites = listOf(
        PrivateSuite(
            id = "suite_a",
            name = "Baby & Parent Hydro Oasis",
            category = "Infant & Toddler Aquatic Sanctuary",
            temp = "32.5°C Heated",
            capacity = "Up to 4 Parents & Babies",
            price = 60,
            features = listOf("Gentle bubble massage jets", "Anti-microbial UV saline water", "Safety shallow steps")
        ),
        PrivateSuite(
            id = "suite_b",
            name = "Ladies Sanctuary Private Pool",
            category = "100% Female-Only Private Deck",
            temp = "31.0°C Heated",
            capacity = "Exclusive Private Group",
            price = 75,
            features = listOf("Complete frosted privacy doors", "Private sauna & herbal tea bar", "Female lifeguards")
        ),
        PrivateSuite(
            id = "suite_c",
            name = "Flume Endless-Current Chamber",
            category = "High-Velocity Biomechanics Lab",
            temp = "28.5°C Speed Water",
            capacity = "Single Swimmer + Coach",
            price = 90,
            features = listOf("Adjustable 0-2:00/100m flow speed", "Underwater floor & side mirrors", "Dual 4K telemetry cameras")
        )
    )

    // Gear Add-ons
    var addUnderwaterVideo by remember { mutableStateOf(false) }
    var addPowerParachute by remember { mutableStateOf(false) }
    var addTempoTrainer by remember { mutableStateOf(false) }

    // Swimmer Info
    var swimmerName by remember { mutableStateOf("Alexander Vance") }
    var bookingNotes by remember { mutableStateOf("") }

    // Active Pass Confirmation Dialog
    var confirmedPass by remember { mutableStateOf<BookingSession?>(null) }
    var showPassModal by remember { mutableStateOf(false) }

    // Dynamic Price Calculation
    val basePrice = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) 25 else (privateSuites.find { it.id == selectedSuiteId }?.price ?: 60)
    val coachPrice = selectedCoach.ratePerHour
    val gearPrice = (if (addUnderwaterVideo) 25 else 0) + (if (addPowerParachute) 15 else 0) + (if (addTempoTrainer) 10 else 0)
    val totalPrice = basePrice + coachPrice + gearPrice

    // Pulsing animation for selected lane
    val infiniteTransition = rememberInfiniteTransition(label = "lane_water")
    val waterPulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "water_pulse"
    )

    Box(modifier = modifier.fillMaxSize().background(MidnightNavy)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            // Header Bar
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(SurfaceNavy, MidnightNavy)))
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = ChampagneGold,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "FACILITY RESERVATIONS",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = AquaCyan,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.5.sp
                                )
                            }
                            Text(
                                text = "Interactive 50m Pool Deck & Private Hydro Suites",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                        LuxuryBadge(text = "Live Deck", isGold = true, icon = Icons.Default.Pool)
                    }
                }
            }

            item {
                AnimatedWaterWaves(height = 24.dp)
            }

            // 1. Interactive Calendar Date Strip
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "OCTOBER 2026",
                                color = TextPrimary,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            LuxuryBadge(text = "${selectedDay.dayOfWeek}, ${selectedDay.dateString}")
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(AquaCyan))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Open", color = TextSecondary, fontSize = 10.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(ChampagneGold))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Private", color = TextSecondary, fontSize = 10.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Horizontal Calendar Strip
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(daysList) { day ->
                            val isSelected = day.dayOfMonth == selectedDay.dayOfMonth
                            val scale by animateFloatAsState(if (isSelected) 1.05f else 1f, label = "day_scale")

                            Box(
                                modifier = Modifier
                                    .scale(scale)
                                    .width(62.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (isSelected) Brush.verticalGradient(listOf(SurfaceCard, DeepAqua.copy(alpha = 0.5f)))
                                        else Brush.verticalGradient(listOf(SurfaceNavy, DeepAbyss))
                                    )
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) AquaCyan else BorderSubtle,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable {
                                        haptic(25)
                                        selectedDay = day
                                    }
                                    .padding(vertical = 12.dp)
                                    .testTag("calendar_day_${day.dayOfMonth}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = day.dayOfWeek,
                                        color = if (isSelected) AquaCyan else TextMuted,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${day.dayOfMonth}",
                                        color = if (isSelected) TextPrimary else TextSecondary,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .size(5.dp)
                                                .clip(CircleShape)
                                                .background(if (day.isAvailable) AquaCyan else TextMuted)
                                        )
                                        if (day.hasPrivateSlot) {
                                            Box(
                                                modifier = Modifier
                                                    .size(5.dp)
                                                    .clip(CircleShape)
                                                    .background(ChampagneGold)
                                            )
                                        }
                                        if (day.hasTeamSprintBlock) {
                                            Box(
                                                modifier = Modifier
                                                    .size(5.dp)
                                                    .clip(CircleShape)
                                                    .background(CoralRed)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(18.dp)) }

            // 2. Segmented Facility Area Toggle
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DeepAbyss)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                        .padding(4.dp)
                ) {
                    FacilityArea.values().forEach { area ->
                        val isSelected = area == selectedFacilityArea
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) AquaCyan else Color.Transparent)
                                .clickable {
                                    haptic(30)
                                    selectedFacilityArea = area
                                }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = area.title,
                                color = if (isSelected) MidnightNavy else TextSecondary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // 3. Interactive Visual Map
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) "INTERACTIVE POOL DECK (50M)" else "HYDROTHERAPY SUITES",
                            color = ChampagneGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) "Tap lane to select" else "Tap suite to reserve",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) {
                        // Olympic 50m Pool Visual Interactive Graphic
                        Olympic50mVisualPoolMap(
                            selectedLane = selectedLaneNumber,
                            waterPulse = waterPulse,
                            onLaneSelected = { lane ->
                                haptic(35)
                                selectedLaneNumber = lane
                            }
                        )
                    } else {
                        // Private Suites Visual Cards
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            privateSuites.forEach { suite ->
                                val isSelected = suite.id == selectedSuiteId
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = if (isSelected) 1.5.dp else 1.dp,
                                            color = if (isSelected) ChampagneGold else BorderSubtle,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .clickable {
                                            haptic(30)
                                            selectedSuiteId = suite.id
                                        }
                                        .testTag("suite_item_${suite.id}"),
                                    color = if (isSelected) SurfaceCard else DeepAbyss
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = suite.name,
                                                    color = TextPrimary,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp
                                                )
                                                Text(
                                                    text = suite.category,
                                                    color = AquaCyan,
                                                    fontSize = 11.sp
                                                )
                                            }
                                            Text(
                                                text = "$${suite.price}/hr",
                                                color = ChampagneGold,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 16.sp
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(Icons.Default.Thermostat, contentDescription = null, tint = ChampagneGold, modifier = Modifier.size(14.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(suite.temp, color = TextSecondary, fontSize = 11.sp)
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(Icons.Default.Person, contentDescription = null, tint = AquaCyan, modifier = Modifier.size(14.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(suite.capacity, color = TextSecondary, fontSize = 11.sp)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            suite.features.forEach { feat ->
                                                LuxuryBadge(text = feat, isGold = isSelected)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // 4. Time Slot Selection
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "SELECT SESSION TIME SLOT",
                        color = ChampagneGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        timeSlots.forEach { slot ->
                            val isSelected = slot == selectedTimeSlot
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) AquaCyan else BorderSubtle,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        haptic(20)
                                        selectedTimeSlot = slot
                                    },
                                color = if (isSelected) AquaCyan.copy(alpha = 0.15f) else SurfaceNavy
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.Schedule,
                                            contentDescription = null,
                                            tint = if (isSelected) AquaCyan else TextMuted,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = slot,
                                            color = if (isSelected) AquaCyan else TextPrimary,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                    if (isSelected) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AquaCyan, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // 5. Coach Add-on Selection
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "MASTER COACH ASSIGNMENT (OPTIONAL)",
                        color = ChampagneGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(coaches) { coach ->
                            val isSelected = coach.id == selectedCoach.id
                            Surface(
                                modifier = Modifier
                                    .width(180.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) ChampagneGold else BorderSubtle,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable {
                                        haptic(25)
                                        selectedCoach = coach
                                    },
                                color = if (isSelected) SurfaceCard else DeepAbyss
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .clip(CircleShape)
                                                .background(if (isSelected) ChampagneGold.copy(alpha = 0.2f) else SurfaceNavy),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Default.Person,
                                                contentDescription = null,
                                                tint = if (isSelected) ChampagneGold else TextSecondary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        Text(
                                            text = if (coach.ratePerHour > 0) "+$${coach.ratePerHour}" else "Free",
                                            color = if (isSelected) ChampagneGold else TextMuted,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = coach.name,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = coach.specialty,
                                        color = TextSecondary,
                                        fontSize = 10.sp,
                                        maxLines = 2,
                                        lineHeight = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // 6. High-Tech Aquatic Gear Add-ons
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "ACADEMY TELEMETRY & GEAR ADD-ONS",
                        color = ChampagneGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                        color = DeepAbyss
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            // Underwater 4K Video
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Videocam, contentDescription = null, tint = AquaCyan, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Underwater 4K Video Biomechanics", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("Multi-angle catch & pull diagnostics (+$25)", color = TextMuted, fontSize = 10.sp)
                                    }
                                }
                                Switch(
                                    checked = addUnderwaterVideo,
                                    onCheckedChange = {
                                        haptic(20)
                                        addUnderwaterVideo = it
                                    },
                                    colors = SwitchDefaults.colors(checkedThumbColor = MidnightNavy, checkedTrackColor = AquaCyan)
                                )
                            }

                            Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(BorderSubtle))

                            // Resisted Parachute & Drag Belt
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Speed, contentDescription = null, tint = ChampagneGold, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Sprint Resisted Drag Parachute", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("Sprint power tower & tempo resistance (+$15)", color = TextMuted, fontSize = 10.sp)
                                    }
                                }
                                Switch(
                                    checked = addPowerParachute,
                                    onCheckedChange = {
                                        haptic(20)
                                        addPowerParachute = it
                                    },
                                    colors = SwitchDefaults.colors(checkedThumbColor = MidnightNavy, checkedTrackColor = ChampagneGold)
                                )
                            }

                            Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(BorderSubtle))

                            // Finis Tempo Metronome
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.HotTub, contentDescription = null, tint = AquaCyan, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Underwater Tempo Metronome (SPM)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("Acoustic goggle metronome for cadence pacing (+$10)", color = TextMuted, fontSize = 10.sp)
                                    }
                                }
                                Switch(
                                    checked = addTempoTrainer,
                                    onCheckedChange = {
                                        haptic(20)
                                        addTempoTrainer = it
                                    },
                                    colors = SwitchDefaults.colors(checkedThumbColor = MidnightNavy, checkedTrackColor = AquaCyan)
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // 7. Swimmer Name & Booking Summary
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "SWIMMER CREDENTIALS",
                        color = ChampagneGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = swimmerName,
                        onValueChange = { swimmerName = it },
                        label = { Text("Registered Swimmer Name", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth().testTag("booking_swimmer_name"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = AquaCyan,
                            unfocusedBorderColor = BorderSubtle,
                            focusedContainerColor = SurfaceCard,
                            unfocusedContainerColor = SurfaceCard
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Live Pricing Breakdown & Confirmation Card
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .border(1.5.dp, Brush.horizontalGradient(listOf(ChampagneGold, AquaCyan)), RoundedCornerShape(20.dp)),
                        color = SurfaceCard
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("RESERVATION TOTAL", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                    Text("$$totalPrice USD", color = ChampagneGold, fontSize = 24.sp, fontWeight = FontWeight.Black)
                                }
                                LuxuryBadge(
                                    text = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) "Lane $selectedLaneNumber" else "Private Suite",
                                    isGold = true
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${selectedDay.dayOfWeek}, ${selectedDay.dateString} • $selectedTimeSlot",
                                color = TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Coach: ${selectedCoach.name} " + if (gearPrice > 0) "• Gear Add-ons included" else "",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    haptic(60)
                                    val sessionTitle = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) {
                                        "Olympic 50m Pool • Lane $selectedLaneNumber"
                                    } else {
                                        privateSuites.find { it.id == selectedSuiteId }?.name ?: "Private Sanctuary Suite"
                                    }

                                    val booking = BookingSession(
                                        id = "bk_" + System.currentTimeMillis().toString().takeLast(6),
                                        programId = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) "lane_$selectedLaneNumber" else selectedSuiteId,
                                        programTitle = sessionTitle,
                                        category = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) ProgramCategory.COMPETITIVE else ProgramCategory.WOMEN,
                                        date = "${selectedDay.dayOfWeek}, ${selectedDay.dateString}",
                                        timeSlot = selectedTimeSlot,
                                        laneNumber = if (selectedFacilityArea == FacilityArea.OLYMPIC_50M) selectedLaneNumber else 0,
                                        coachName = selectedCoach.name,
                                        swimmerName = swimmerName.ifBlank { "Member Swimmer" },
                                        notes = if (gearPrice > 0) "Telemetry gear add-on requested ($totalPrice total)" else "Standard slot",
                                        status = "Confirmed VIP Access"
                                    )

                                    confirmedPass = booking
                                    showPassModal = true
                                    onBookingConfirmed(booking)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .testTag("reserve_facility_button"),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AquaCyan, contentColor = MidnightNavy)
                            ) {
                                Icon(Icons.Default.ConfirmationNumber, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reserve & Generate Olympic Pass", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(50.dp)) }
        }

        // Animated Golden Boarding Pass Modal Overlay
        if (showPassModal && confirmedPass != null) {
            val session = confirmedPass!!
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .clickable { showPassModal = false }
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(2.dp, ChampagneGold, RoundedCornerShape(24.dp))
                        .clickable(enabled = false) {}, // prevent clicking through
                    color = DeepAbyss
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.verticalGradient(listOf(SurfaceNavy, DeepAbyss)))
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Pass Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(ChampagneGold.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ChampagneGold, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("OFFICIAL ACCESS PASS", color = ChampagneGold, fontWeight = FontWeight.Black, fontSize = 12.sp, letterSpacing = 1.sp)
                                    Text(session.id.uppercase(), color = TextSecondary, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                                }
                            }
                            IconButton(onClick = { showPassModal = false }) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Large Destination Banner
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Brush.horizontalGradient(listOf(DeepAqua.copy(alpha = 0.4f), SurfaceNavy)))
                                .border(1.dp, AquaCyan.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = session.programTitle,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (session.laneNumber > 0) "Olympic FINA Fast Lane #${session.laneNumber}" else "Exclusive Private Access",
                                    color = AquaCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Session Details Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("MEMBER", color = TextMuted, fontSize = 10.sp)
                                Text(session.swimmerName, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("SCHEDULE", color = TextMuted, fontSize = 10.sp)
                                Text(session.date, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                Text(session.timeSlot.substringBefore(" ("), color = ChampagneGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("COACH", color = TextMuted, fontSize = 10.sp)
                                Text(session.coachName, color = AquaCyan, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("ENTRY GATE", color = TextMuted, fontSize = 10.sp)
                                Text("Turnstile B-North", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Simulated Luxury Digital QR Barcode
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.08f))
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.QrCode, contentDescription = null, tint = AquaCyan, modifier = Modifier.size(36.dp))
                                Text(
                                    text = "PSA-GATE-ENTRY-OCT26-VERIFIED",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    color = ChampagneGold,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                showPassModal = false
                                Toast.makeText(context, "Pass saved to Member Passport", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("dismiss_modal_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AquaCyan, contentColor = MidnightNavy)
                        ) {
                            Text("Confirmed • View in Passport", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Olympic50mVisualPoolMap(
    selectedLane: Int,
    waterPulse: Float,
    onLaneSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp)),
        color = DeepAbyss
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Pool Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(AquaCyan))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Selected", color = AquaCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(ChampagneGold))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Center Fast Lane", color = ChampagneGold, fontSize = 11.sp)
                }
                Text("Length: 50m • Depth: 2.5m", color = TextMuted, fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 8 Visual Interactive Lanes (Stacked Vertically representing pool lanes from Wall A to Wall B)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                (1..8).forEach { lane ->
                    val isSelected = lane == selectedLane
                    val isFastLane = lane == 4 || lane == 5

                    val laneBgColor by animateColorAsState(
                        targetValue = when {
                            isSelected -> AquaCyan.copy(alpha = 0.28f + (waterPulse * 0.12f))
                            isFastLane -> SurfaceCard
                            else -> SurfaceNavy
                        },
                        animationSpec = tween(250),
                        label = "lane_bg"
                    )

                    val borderLineColor by animateColorAsState(
                        targetValue = if (isSelected) AquaCyan else BorderSubtle.copy(alpha = 0.8f),
                        animationSpec = tween(250),
                        label = "lane_border"
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(if (isSelected) 1.5.dp else 1.dp, borderLineColor, RoundedCornerShape(10.dp))
                            .clickable { onLaneSelected(lane) }
                            .testTag("pool_lane_$lane"),
                        color = laneBgColor
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Lane Number Badge
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) AquaCyan else if (isFastLane) ChampagneGold.copy(alpha = 0.2f) else SurfaceNavy),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$lane",
                                        color = if (isSelected) MidnightNavy else if (isFastLane) ChampagneGold else TextPrimary,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 12.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(
                                        text = "Olympic Lane $lane",
                                        color = if (isSelected) AquaCyan else TextPrimary,
                                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = if (isFastLane) "Speed Qualifier / Continuous Pace" else "Aerobic / Technique Lane",
                                        color = if (isFastLane) ChampagneGold else TextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            // Lane status tag
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isSelected) {
                                    LuxuryBadge(text = "Selected", isGold = false, icon = Icons.Default.Check)
                                } else if (isFastLane) {
                                    LuxuryBadge(text = "Fast Lane", isGold = true, icon = Icons.Default.Speed)
                                } else {
                                    Text("Open", color = TextMuted, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Pool Wall Turnstiles Indication
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Start Blocks & Omega Touchpads", color = TextMuted, fontSize = 10.sp)
                Text("15m Underwater Breakout Marker", color = AquaCyan.copy(alpha = 0.7f), fontSize = 10.sp)
            }
        }
    }
}
