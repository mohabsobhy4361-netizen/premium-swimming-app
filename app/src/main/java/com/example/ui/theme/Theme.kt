package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PremiumDarkColorScheme = darkColorScheme(
    primary = NavyPrimary,
    onPrimary = NavyOnPrimary,
    primaryContainer = DeepAqua,
    onPrimaryContainer = IceBlue,
    secondary = NavySecondary,
    onSecondary = NavyOnSecondary,
    secondaryContainer = DarkGold,
    onSecondaryContainer = ChampagneGold,
    tertiary = NavyTertiary,
    background = NavyBackground,
    surface = NavySurface,
    surfaceVariant = NavySurfaceVariant,
    onBackground = NavyOnSurface,
    onSurface = NavyOnSurface,
    onSurfaceVariant = TextSecondary,
    outline = NavyOutline,
    outlineVariant = BorderSubtle
)

private val PremiumLightColorScheme = lightColorScheme(
    primary = LightAquaPrimary,
    onPrimary = LightSurface,
    primaryContainer = LightAquaContainer,
    onPrimaryContainer = LightOnAquaContainer,
    secondary = LightGoldSecondary,
    onSecondary = LightSurface,
    secondaryContainer = LightGoldContainer,
    onSecondaryContainer = LightOnGoldContainer,
    tertiary = DeepAqua,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
    outlineVariant = LightBorder.copy(alpha = 0.5f)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) PremiumDarkColorScheme else PremiumLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = (if (darkTheme) MidnightNavy else LightBackground).toArgb()
                window.navigationBarColor = (if (darkTheme) DeepAbyss else LightSurface).toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
