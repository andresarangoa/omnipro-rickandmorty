package com.app.omnipro_test_rm.ui.config

data class TestConfig(
    val darkModeEnabled: Boolean = false,
    val localeQualifier: String = "+en",
    val orientationQualifier: String = "+port",
    val uiModeQualifier: String = if (darkModeEnabled) "+night" else "+notnight",
    val fontScale: FontScale = FontScale.FONT_NORMAL,
    val displayScale: DisplayScale = DisplayScale.DISPLAY_NORMAL,
    val maxPercentDifference: Float = 0.01f
)