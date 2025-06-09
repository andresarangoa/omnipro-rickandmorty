package com.app.omnipro_test_rm.ui.config

data class TestCase(
    val deviceType: DeviceType,
    val testClassName: String,
    val testName: String,
    val testConfig: TestConfig = TestConfig()
) {
    val screenshotId: String = deviceType.name +
            appendFontScaleIfNotDefault() +
            appendDisplayScaleIfNotDefault() +
            appendDarkTheme()

    private fun appendFontScaleIfNotDefault() = with(testConfig) {
        if (fontScale != FontScale.FONT_NORMAL) {
            "_${fontScale.name}"
        } else ""
    }

    private fun appendDisplayScaleIfNotDefault() = with(testConfig) {
        if (displayScale != DisplayScale.DISPLAY_NORMAL) {
            "_${displayScale.name}"
        } else ""
    }

    private fun appendDarkTheme() = if (testConfig.darkModeEnabled) {
        "_${Constants.Theme.DARK}"
    } else ""
}