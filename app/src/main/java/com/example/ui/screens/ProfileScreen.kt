package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BookingSession
import com.example.model.SwimmerProfile
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

import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import com.example.data.AppLanguage
import com.example.data.AppLocalization

@Composable
fun ProfileScreen(
    bookings: List<BookingSession>,
    onCancelBooking: (String) -> Unit,
    isDarkMode: Boolean = true,
    onToggleTheme: () -> Unit = {},
    currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    onToggleLanguage: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val profile = remember { SwimmerProfile() }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "MEMBER PASSPORT",
                        style = MaterialTheme.typography.titleLarge,
                        color = AquaCyan,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Premium Swimming Academy • ID: ${profile.academyId}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                LuxuryBadge(text = "Diamond VIP", isGold = true, icon = Icons.Default.Shield)
            }
        }

        AnimatedWaterWaves(height = 24.dp)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // VIP Diamond Membership Card
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            1.5.dp,
                            Brush.linearGradient(
                                listOf(ChampagneGold, AquaCyan, RadiantGold)
                            ),
                            RoundedCornerShape(24.dp)
                        ),
                    color = DeepAbyss
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(SurfaceNavy.copy(alpha = 0.9f), DeepAbyss)
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "PREMIUM SWIMMING ACADEMY",
                                    color = ChampagneGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 2.sp
                                )
                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = "Digital Pass",
                                tint = AquaCyan,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = profile.membershipTier,
                            color = AquaCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Quick Row inside Card
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("COMPLETED", color = TextMuted, fontSize = 10.sp)
                                Text("${profile.completedSessions} Sessions", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Column {
                                Text("TOTAL LAPS", color = TextMuted, fontSize = 10.sp)
                                Text("${profile.totalLaps} Laps (63km)", color = ChampagneGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("STATUS", color = TextMuted, fontSize = 10.sp)
                                Text("Active Olympic Pass", color = AquaCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // Active Lane Bookings Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upcoming Reserved Sessions (${bookings.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (bookings.isEmpty()) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp)),
                        color = SurfaceCard
                    ) {
                        Text(
                            text = "No upcoming pool sessions reserved. Browse Disciplines to book a lane.",
                            color = TextMuted,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(bookings) { booking ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                        color = SurfaceCard
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LuxuryBadge(text = "Lane #${booking.laneNumber}", isGold = true)
                                Text(
                                    text = booking.status,
                                    color = AquaCyan,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = booking.programTitle,
                                style = MaterialTheme.typography.titleSmall,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "${booking.date} • ${booking.timeSlot}",
                                color = ChampagneGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "Coach: ${booking.coachName}",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )

                            if (booking.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Focus: ${booking.notes}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Cancel Reservation",
                                    color = CoralRed,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .clickable {
                                            onCancelBooking(booking.id)
                                            Toast.makeText(context, "Reservation cancelled", Toast.LENGTH_SHORT).show()
                                        }
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Personal Bests Section
            item {
                Text(
                    text = "Official Personal Bests (FINA Timed)",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                    color = DeepAbyss
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        profile.personalBests.forEachIndexed { index, pb ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "${pb.distance} ${pb.stroke}",
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = pb.dateAchieved,
                                        color = TextMuted,
                                        fontSize = 11.sp
                                    )
                                }
                                Text(
                                    text = pb.time,
                                    color = AquaCyan,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 15.sp
                                )
                            }
                            if (index < profile.personalBests.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.5.dp)
                                        .background(BorderSubtle)
                                )
                            }
                        }
                    }
                }
            }

            // Badges & Achievements
            item {
                Text(
                    text = "Academy Badges & Honors",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    profile.badges.forEach { badge ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    1.dp,
                                    if (badge.unlocked) AquaCyan.copy(alpha = 0.4f) else BorderSubtle,
                                    RoundedCornerShape(12.dp)
                                ),
                            color = if (badge.unlocked) SurfaceCard else DeepAbyss
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(if (badge.unlocked) ChampagneGold.copy(alpha = 0.2f) else SurfaceNavy),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (badge.unlocked) Icons.Default.Star else Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = if (badge.unlocked) ChampagneGold else TextMuted,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = badge.name,
                                        color = if (badge.unlocked) TextPrimary else TextMuted,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = badge.description,
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // App Preferences (Theme & Language)
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                    color = DeepAbyss
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) "تفضيلات التطبيق والمظهر" else "APP PREFERENCES & DISPLAY",
                            color = ChampagneGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        // Dark Mode Toggle Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = null,
                                    tint = AquaCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (currentLanguage == AppLanguage.ARABIC) "الوضع الداكن (Dark Mode)" else "Dark Theme",
                                        color = TextPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isDarkMode) "Deep Oceanic Dark Luxury" else "Crisp Mediterranean Day Light",
                                        color = TextMuted,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { onToggleTheme() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MidnightNavy,
                                    checkedTrackColor = AquaCyan
                                )
                            )
                        }

                        Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(BorderSubtle))

                        // Language Toggle Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = null,
                                    tint = ChampagneGold,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (currentLanguage == AppLanguage.ARABIC) "لغة التطبيق (Language)" else "App Language",
                                        color = TextPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (currentLanguage == AppLanguage.ARABIC) "العربية (RTL)" else "English (LTR)",
                                        color = ChampagneGold,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            Button(
                                onClick = { onToggleLanguage() },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SurfaceNavy,
                                    contentColor = AquaCyan
                                )
                            ) {
                                Text(
                                    text = if (currentLanguage == AppLanguage.ENGLISH) "Switch to عربي" else "Switch to English",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Academy Contact & Concierge
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                    color = SurfaceNavy
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "ACADEMY CONCIERGE & POOL DECK",
                            color = ChampagneGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Olympic 50m & Warm Hydrotherapy Pools • FINA Certified",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Direct Line: +1 (800) 555-SWIM • concierge@premiumswim.com",
                            color = AquaCyan,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
