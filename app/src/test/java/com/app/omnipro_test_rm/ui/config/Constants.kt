package com.app.omnipro_test_rm.ui.config

/**
 * Constants used throughout the testing framework.
 */
object Constants {

    /**
     * Screenshot configuration for snapshot testing.
     */
    object Screenshot {
        const val SCREENSHOT_EXTENSION = ".png"

        // Base paths for different screen types
        const val PATH_CHARACTER_DETAIL_SCREEN = "screenshots/character_detail"
        const val PATH_CHARACTERS_SCREEN = "screenshots/characters"
        const val PATH_AUTHENTICATION_START_SCREEN = "screenshots/authentication/start"

    }

    /**
     * Theme configuration for tests.
     */
    object Theme {
        const val LIGHT_MODE = "light"
        const val DARK = "dark"
        const val SYSTEM_DEFAULT = "system"
    }

    /**
     * Locale configuration for tests.
     */
    object Locale {
        const val ENGLISH = "en"
        const val SPANISH = "es"
        const val FRENCH = "fr"
        const val DEFAULT = SPANISH
    }
}