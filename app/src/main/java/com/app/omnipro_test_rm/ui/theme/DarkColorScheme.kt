package com.app.omnipro_test_rm.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = RickMortyColors.PortalBlue,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF003A5C),
    onPrimaryContainer = Color(0xFFB3E5FC),
    
    secondary = RickMortyColors.PortalGreen,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF2E7D32),
    onSecondaryContainer = Color(0xFFC8E6C9),
    
    tertiary = RickMortyColors.AccentDark,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF6A1B9A),
    onTertiaryContainer = Color(0xFFE1BEE7),
    
    error = RickMortyColors.Error,
    onError = Color.White,
    errorContainer = Color(0xFFD32F2F),
    onErrorContainer = Color(0xFFFFCDD2),
    
    background = RickMortyColors.DeepSpace,
    onBackground = RickMortyColors.TextPrimaryDark,
    
    surface = RickMortyColors.SurfaceDark,
    onSurface = RickMortyColors.TextPrimaryDark,
    surfaceVariant = RickMortyColors.CardBackgroundDark,
    onSurfaceVariant = RickMortyColors.TextSecondaryDark,
    
    outline = Color(0xFF424242),
    outlineVariant = Color(0xFF303030),
    
    scrim = Color.Black.copy(alpha = 0.6f),
    inverseSurface = Color(0xFFF5F5F5),
    inverseOnSurface = Color(0xFF2A2A2A),
    inversePrimary = RickMortyColors.ScienceBlue
)