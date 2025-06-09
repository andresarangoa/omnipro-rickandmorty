package com.app.omnipro_test_rm.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = RickMortyColors.ScienceBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF001F33),
    
    secondary = RickMortyColors.PortalGreen,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFE8F5E8),
    onSecondaryContainer = Color(0xFF1B5E20),
    
    tertiary = RickMortyColors.Accent,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF3E5F5),
    onTertiaryContainer = Color(0xFF4A148C),
    
    error = RickMortyColors.Error,
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFFB71C1C),
    
    background = Color(0xFFFAFBFC),
    onBackground = RickMortyColors.TextPrimary,
    
    surface = RickMortyColors.Surface,
    onSurface = RickMortyColors.TextPrimary,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = RickMortyColors.TextSecondary,
    
    outline = Color(0xFFE0E0E0),
    outlineVariant = Color(0xFFF0F0F0),
    
    scrim = Color.Black.copy(alpha = 0.4f),
    inverseSurface = Color(0xFF2A2A2A),
    inverseOnSurface = Color(0xFFE0E0E0),
    inversePrimary = RickMortyColors.PortalBlue
)