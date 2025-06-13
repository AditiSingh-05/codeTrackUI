package com.example.dummyui.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

data class CodeTrackColors(
    val primaryButton : androidx.compose.ui.graphics.Color,
    val secondaryButton: androidx.compose.ui.graphics.Color,
    val textPrimary: androidx.compose.ui.graphics.Color,
    val textSecondary: androidx.compose.ui.graphics.Color,
    val background: androidx.compose.ui.graphics.Color,
    val surface: androidx.compose.ui.graphics.Color,
    val cardBackground: androidx.compose.ui.graphics.Color,
    val success: androidx.compose.ui.graphics.Color,
    val error: androidx.compose.ui.graphics.Color,
    val warning: androidx.compose.ui.graphics.Color,
    val divider: androidx.compose.ui.graphics.Color
)

val LocalCodeTrackColors = staticCompositionLocalOf {
    CodeTrackColors(
        primaryButton = PrimaryDark,
        secondaryButton = SecondaryDark,
        textPrimary = TextPrimaryDark,
        textSecondary = TextSecondaryDark,
        background = BackgroundDark,
        surface = SurfaceDark,
        cardBackground = Color(0xFF252525),
        success = SuccessGreen,
        error = ErrorRed,
        warning = WarningYellow,
        divider = Color(0xFF3A3A3A)
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.Companion.Black,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.Companion.Black,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
@Composable
fun DummyuiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val materialColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val customColors = if (darkTheme) {
        CodeTrackColors(
            primaryButton = PrimaryDark,
            secondaryButton = SecondaryDark,
            textPrimary = TextPrimaryDark,
            textSecondary = TextSecondaryDark,
            background = BackgroundDark,
            surface = SurfaceDark,
            cardBackground = Color(0xFF252525),
            success = SuccessGreen,
            error = ErrorRed,
            warning = WarningYellow,
            divider = Color(0xFF3A3A3A)
        )
    } else {
        CodeTrackColors(
            primaryButton = PrimaryLight,
            secondaryButton = SecondaryLight,
            textPrimary = TextPrimaryLight,
            textSecondary = TextSecondaryLight,
            background = BackgroundLight,
            surface = SurfaceLight,
            cardBackground = Color.White,
            success = SuccessGreen,
            error = ErrorRed,
            warning = WarningYellow,
            divider = Color(0xFFE0E0E0)
        )
    }

    CompositionLocalProvider(LocalCodeTrackColors provides customColors) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = Typography,
//            shapes = Shapes,
            content = content
        )
    }
}

object DummyuiThemeColors {
    val colors: CodeTrackColors
        @Composable
        get() = LocalCodeTrackColors.current
}