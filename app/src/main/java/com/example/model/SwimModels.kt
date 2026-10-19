package com.example.model

enum class ProgramCategory(val displayName: String, val shortDesc: String) {
    BABIES("Babies & Infants", "Ages 3 mo - 3 yrs • Gentle water acclimation & infant safety"),
    CHILDREN("Children & Youth", "Ages 4 - 14 yrs • Foundational strokes & junior mastery"),
    WOMEN("Women Sanctuary", "All ages • 100% private ladies-only pool hours & aqua fitness"),
    MEN("Men Performance", "Adult & Masters • High-intensity cardio, biomechanics & triathlon"),
    COMPETITIVE("Competitive Squad", "FINA / Junior & Senior Nationals • Elite race-pace training")
}

data class SwimProgram(
    val id: String,
    val title: String,
    val category: ProgramCategory,
    val ageGroup: String,
    val skillLevel: String,
    val durationWeeks: Int,
    val sessionsPerWeek: String,
    val coachRatio: String,
    val poolTemp: String,
    val scheduleSummary: String,
    val description: String,
    val highlights: List<String>,
    val curriculumModules: List<String>,
    val priceMonthly: String,
    val leadCoach: String,
    val coachRole: String,
    val badgeLabel: String = ""
)

data class BookingSession(
    val id: String,
    val programId: String,
    val programTitle: String,
    val category: ProgramCategory,
    val date: String,
    val timeSlot: String,
    val laneNumber: Int,
    val coachName: String,
    val swimmerName: String,
    val notes: String = "",
    val status: String = "Confirmed",
    val timestamp: Long = System.currentTimeMillis()
)

data class BadgeItem(
    val id: String,
    val name: String,
    val description: String,
    val unlocked: Boolean,
    val iconName: String
)

data class PersonalBest(
    val stroke: String,
    val distance: String,
    val time: String,
    val dateAchieved: String
)

data class SwimmerProfile(
    val name: String = "Alexander Vance",
    val membershipTier: String = "Diamond Aquatics Member",
    val academyId: String = "PSA-2026-8849",
    val currentLevel: String = "Level 5 - Advanced Stroke Mechanics",
    val completedSessions: Int = 42,
    val totalLaps: Int = 1260,
    val personalBests: List<PersonalBest> = listOf(
        PersonalBest("Freestyle", "50m", "26.42s", "Oct 12, 2026"),
        PersonalBest("Freestyle", "100m", "58.10s", "Sep 28, 2026"),
        PersonalBest("Butterfly", "50m", "28.90s", "Oct 05, 2026"),
        PersonalBest("Breaststroke", "50m", "33.15s", "Aug 19, 2026")
    ),
    val badges: List<BadgeItem> = listOf(
        BadgeItem("1", "Trident Gold", "Completed 1,000+ competition laps", true, "Star"),
        BadgeItem("2", "Streamline Master", "Perfect 15m underwater breakout verified by coach", true, "Speed"),
        BadgeItem("3", "Cold Splash", "Sub-1min 100m Freestyle milestone", true, "EmojiEvents"),
        BadgeItem("4", "Iron Lung", "50m underwater dolphin kick single breath", false, "Lock")
    )
)

data class AiCoachMessage(
    val id: String,
    val isUser: Boolean,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val drillRecommendations: List<String> = emptyList(),
    val isStreaming: Boolean = false
)

enum class StrokeType(val displayName: String) {
    FREESTYLE("Freestyle (Front Crawl)"),
    BACKSTROKE("Backstroke"),
    BREASTSTROKE("Breaststroke"),
    BUTTERFLY("Butterfly"),
    STREAMLINE_START("Dive Start & Streamline Breakout"),
    FLIP_TURN("Tumble Turn & Push-Off")
}
