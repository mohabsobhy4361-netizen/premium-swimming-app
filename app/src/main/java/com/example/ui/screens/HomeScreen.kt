package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.BookingSession
import com.example.model.ProgramCategory
import com.example.ui.components.AnimatedWaterWaves
import com.example.ui.components.FacilityMetricCard
import com.example.ui.components.LuxuryBadge
import com.example.ui.theme.AquaCyan
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.DeepAbyss
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

import com.example.data.AppLanguage
import com.example.data.AppLocalization

@Composable
fun HomeScreen(
    onNavigateCategory: (ProgramCategory) -> Unit,
    onNavigateFacility: () -> Unit,
    onNavigateAiCoach: () -> Unit,
    onNavigateTimer: () -> Unit,
    onNavigateProfile: () -> Unit,
    upcomingBookings: List<BookingSession>,
    currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    modifier: Modifier = Modifier
) {
    val strings = remember(currentLanguage) { AppLocalization.getStrings(currentLanguage) }
    val infiniteTransition = rememberInfiniteTransition(label = "gold_glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Hero Banner with Olympic Pool Image
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                // Background Hero Image
                Image(
                    painter = painterResource(id = R.drawable.hero_pool_1792435311318),
                    contentDescription = "Olympic Swimming Facility",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Luxury Dark Vignette Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MidnightNavy.copy(alpha = 0.6f),
                                    MidnightNavy
                                )
                            )
                        )
                )

                // Top Academy Branding Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_swim_logo_1792435297518),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .border(1.dp, ChampagneGold, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "PREMIUM AQUATICS",
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            letterSpacing = 2.sp
                        )
                    }

                    LuxuryBadge(text = "Olympic Standard", isGold = true, icon = Icons.Default.EmojiEvents)
                }

                // Bottom Content on Hero
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "PREMIUM SWIMMING ACADEMY",
                        color = AquaCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Excellence Across Every Age & Stroke",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Babies • Children • Women • Men • Competitive Titans",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Fluid Animated Water Waves
        item {
            AnimatedWaterWaves(height = 30.dp)
        }

        // Facility Key Metrics Row
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = strings.infrastructureTitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = ChampagneGold,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FacilityMetricCard(
                        value = strings.metricLanesValue,
                        label = strings.metricLanesLabel,
                        icon = Icons.Default.Pool,
                        modifier = Modifier.weight(1f),
                        accentColor = AquaCyan
                    )
                    FacilityMetricCard(
                        value = strings.metricPoolTempValue,
                        label = strings.metricPoolTempLabel,
                        icon = Icons.Default.Thermostat,
                        modifier = Modifier.weight(1f),
                        accentColor = ChampagneGold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FacilityMetricCard(
                        value = strings.metricPrivateValue,
                        label = strings.metricPrivateLabel,
                        icon = Icons.Default.Lock,
                        modifier = Modifier.weight(1f),
                        accentColor = Color(0xFFF472B6)
                    )
                    FacilityMetricCard(
                        value = strings.metricRatioValue,
                        label = strings.metricRatioLabel,
                        icon = Icons.Default.ChildCare,
                        modifier = Modifier.weight(1f),
                        accentColor = AquaCyan
                    )
                }
            }
        }

        // Visual Facility Booking Card
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        1.5.dp,
                        Brush.horizontalGradient(listOf(ChampagneGold, AquaCyan)),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onNavigateFacility() }
                    .testTag("home_facility_booking_card"),
                color = SurfaceCard
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(ChampagneGold.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Pool,
                                    contentDescription = null,
                                    tint = ChampagneGold,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Book Facility & Lanes",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Interactive Pool Deck & Hydro Suites",
                                    color = ChampagneGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Book",
                            tint = ChampagneGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Reserve specific 50m Olympic lanes (Lanes 1-8) or book private hydrotherapy & ladies sanctuary suites with live calendar availability.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // AI Coach Teaser Card
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        1.5.dp,
                        AquaCyan.copy(alpha = glowAlpha),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onNavigateAiCoach() }
                    .testTag("home_ai_coach_card"),
                color = DeepAbyss
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(AquaCyan.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = AquaCyan,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Gemini AI Swim Coach",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            LuxuryBadge(text = "Live", isGold = true)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Analyze dropped hips, calculate race-pace splits, or design personalized sprint sets.",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Go",
                        tint = AquaCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Next Upcoming Session Card (if exists)
        if (upcomingBookings.isNotEmpty()) {
            item {
                val next = upcomingBookings.first()
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, ChampagneGold.copy(alpha = 0.7f), RoundedCornerShape(18.dp))
                        .clickable { onNavigateProfile() },
                    color = SurfaceCard
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(ChampagneGold.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Pool, contentDescription = null, tint = ChampagneGold)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "UPCOMING RESERVED LANE #${next.laneNumber}",
                                color = ChampagneGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = next.programTitle,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "${next.date} • Coach ${next.coachName}",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }

                        Text(
                            text = "Pass",
                            color = AquaCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Explore All 5 Academy Training Disciplines
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ACADEMY DISCIPLINES",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "View All 5",
                        color = AquaCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // 1. Babies & Infants
                    CategoryCardItem(
                        title = "Babies & Infants",
                        subtitle = "Ages 3 mo – 3 yrs • 32.5°C warm sensory pool, gentle acclimation & self-rescue",
                        badge = "1:3 Ratio",
                        icon = Icons.Default.ChildCare,
                        accentColor = AquaCyan,
                        onClick = { onNavigateCategory(ProgramCategory.BABIES) }
                    )

                    // 2. Children & Youth
                    CategoryCardItem(
                        title = "Children & Youth",
                        subtitle = "Ages 4 – 14 yrs • 4-Stroke mastery, streamlined propulsion, flip turns",
                        badge = "Youth Squad",
                        icon = Icons.Default.Face,
                        accentColor = ChampagneGold,
                        onClick = { onNavigateCategory(ProgramCategory.CHILDREN) }
                    )

                    // 3. Women Sanctuary
                    CategoryCardItem(
                        title = "Women Sanctuary",
                        subtitle = "100% private ladies-only pool hours, female coaches, prenatal aqua wellness",
                        badge = "100% Private",
                        icon = Icons.Default.Pool,
                        accentColor = Color(0xFFF472B6),
                        onClick = { onNavigateCategory(ProgramCategory.WOMEN) }
                    )

                    // 4. Men Performance
                    CategoryCardItem(
                        title = "Men Performance",
                        subtitle = "Adult swim HIIT, parachute resisted power, biomechanics & triathlon prep",
                        badge = "High Intensity",
                        icon = Icons.Default.FitnessCenter,
                        accentColor = Color(0xFF38BDF8),
                        onClick = { onNavigateCategory(ProgramCategory.MEN) }
                    )

                    // 5. Competitive Teams (Titans)
                    CategoryCardItem(
                        title = "The Titans Competitive Squad",
                        subtitle = "Olympic & National meet preparation, FINA timing blocks, video stroke analysis",
                        badge = "Elite FINA",
                        icon = Icons.Default.EmojiEvents,
                        accentColor = RadiantGold,
                        onClick = { onNavigateCategory(ProgramCategory.COMPETITIVE) }
                    )
                }
            }
        }

        // Poolside Stopwatch Quick Link
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(18.dp))
                    .clickable { onNavigateTimer() },
                color = SurfaceNavy
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AquaCyan.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Timer, contentDescription = null, tint = AquaCyan)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Poolside Precision Chrono",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "50m lap split counter & Stroke Rate (SPM) cadence tap meter",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = AquaCyan)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CategoryCardItem(
    title: String,
    subtitle: String,
    badge: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = SurfaceCard
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    LuxuryBadge(text = badge, isGold = accentColor == ChampagneGold || accentColor == RadiantGold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
