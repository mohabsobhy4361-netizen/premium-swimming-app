package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Pool
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AcademyData
import com.example.data.GeminiCoachService
import com.example.model.BookingSession
import com.example.model.ProgramCategory
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.CalendarMonth
import com.example.ui.screens.AiCoachScreen
import com.example.ui.screens.FacilityBookingScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PoolsideTimerScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProgramsScreen
import com.example.ui.theme.AquaCyan
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.DeepAbyss
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.LightSurface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.data.AppLanguage
import com.example.data.AppLocalization
import com.example.ui.components.AppTopBar

enum class NavDestination(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
    FACILITY("Booking", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth, "nav_facility"),
    PROGRAMS("Disciplines", Icons.Filled.Pool, Icons.Outlined.Pool, "nav_programs"),
    AI_COACH("AI Coach", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome, "nav_ai_coach"),
    PASSPORT("Passport", Icons.Filled.Person, Icons.Outlined.Person, "nav_passport"),
    CHRONO("Chrono", Icons.Filled.Timer, Icons.Outlined.Timer, "nav_chrono")
}

class MainActivity : ComponentActivity() {

    private val geminiCoachService = GeminiCoachService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            var currentLanguage by remember { mutableStateOf(AppLanguage.ENGLISH) }

            MyApplicationTheme(darkTheme = isDarkMode) {
                val layoutDirection = if (currentLanguage == AppLanguage.ARABIC) LayoutDirection.Rtl else LayoutDirection.Ltr
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    MainAppContent(
                        coachService = geminiCoachService,
                        isDarkMode = isDarkMode,
                        onToggleTheme = { isDarkMode = !isDarkMode },
                        currentLanguage = currentLanguage,
                        onToggleLanguage = {
                            currentLanguage = if (currentLanguage == AppLanguage.ENGLISH) AppLanguage.ARABIC else AppLanguage.ENGLISH
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainAppContent(
    coachService: GeminiCoachService,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    currentLanguage: AppLanguage,
    onToggleLanguage: () -> Unit
) {
    var currentDestination by remember { mutableStateOf(NavDestination.HOME) }
    var selectedCategoryForPrograms by remember { mutableStateOf(ProgramCategory.BABIES) }
    val localizedStrings = remember(currentLanguage) { AppLocalization.getStrings(currentLanguage) }

    val bookingsList = remember {
        mutableStateListOf<BookingSession>().apply {
            addAll(AcademyData.sampleBookings)
        }
    }

    // BackHandler to navigate to HOME if on sub-screens
    BackHandler(enabled = currentDestination != NavDestination.HOME) {
        currentDestination = NavDestination.HOME
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme,
                currentLanguage = currentLanguage,
                onToggleLanguage = onToggleLanguage,
                academyTitle = if (currentLanguage == AppLanguage.ARABIC) "أكاديمية السباحة الممتازة" else "PREMIUM AQUATICS"
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    ),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val primaryTabs = listOf(
                    NavDestination.HOME,
                    NavDestination.FACILITY,
                    NavDestination.PROGRAMS,
                    NavDestination.AI_COACH,
                    NavDestination.PASSPORT
                )

                primaryTabs.forEach { destination ->
                    val isSelected = currentDestination == destination
                    val localizedTitle = when (destination) {
                        NavDestination.HOME -> localizedStrings.navHome
                        NavDestination.FACILITY -> localizedStrings.navBooking
                        NavDestination.PROGRAMS -> localizedStrings.navDisciplines
                        NavDestination.AI_COACH -> localizedStrings.navAiCoach
                        NavDestination.PASSPORT -> localizedStrings.navPassport
                        NavDestination.CHRONO -> localizedStrings.navChrono
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentDestination = destination },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                contentDescription = localizedTitle,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = localizedTitle,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (isDarkMode) MidnightNavy else LightSurface,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = if (destination == NavDestination.AI_COACH || destination == NavDestination.FACILITY) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.testTag(destination.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = {
                    if (targetState.ordinal > initialState.ordinal) {
                        (slideInHorizontally { width -> width / 3 } + fadeIn()) togetherWith
                                (slideOutHorizontally { width -> -width / 3 } + fadeOut())
                    } else {
                        (slideInHorizontally { width -> -width / 3 } + fadeIn()) togetherWith
                                (slideOutHorizontally { width -> width / 3 } + fadeOut())
                    }
                },
                label = "screen_nav_transition"
            ) { targetScreen ->
                when (targetScreen) {
                    NavDestination.HOME -> {
                        HomeScreen(
                            onNavigateCategory = { cat ->
                                selectedCategoryForPrograms = cat
                                currentDestination = NavDestination.PROGRAMS
                            },
                            onNavigateFacility = {
                                currentDestination = NavDestination.FACILITY
                            },
                            onNavigateAiCoach = {
                                currentDestination = NavDestination.AI_COACH
                            },
                            onNavigateTimer = {
                                currentDestination = NavDestination.CHRONO
                            },
                            onNavigateProfile = {
                                currentDestination = NavDestination.PASSPORT
                            },
                            upcomingBookings = bookingsList,
                            currentLanguage = currentLanguage
                        )
                    }

                    NavDestination.FACILITY -> {
                        FacilityBookingScreen(
                            onBookingConfirmed = { newBooking ->
                                bookingsList.add(0, newBooking)
                            }
                        )
                    }

                    NavDestination.PROGRAMS -> {
                        ProgramsScreen(
                            initialCategory = selectedCategoryForPrograms,
                            onBookingConfirmed = { newBooking ->
                                bookingsList.add(0, newBooking)
                            }
                        )
                    }

                    NavDestination.AI_COACH -> {
                        AiCoachScreen(coachService = coachService)
                    }

                    NavDestination.CHRONO -> {
                        PoolsideTimerScreen()
                    }

                    NavDestination.PASSPORT -> {
                        ProfileScreen(
                            bookings = bookingsList,
                            onCancelBooking = { bookingId ->
                                bookingsList.removeAll { it.id == bookingId }
                            },
                            isDarkMode = isDarkMode,
                            onToggleTheme = onToggleTheme,
                            currentLanguage = currentLanguage,
                            onToggleLanguage = onToggleLanguage
                        )
                    }
                }
            }
        }
    }
}
