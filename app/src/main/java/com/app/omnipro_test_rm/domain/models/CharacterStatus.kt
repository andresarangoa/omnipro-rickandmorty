package com.app.omnipro_test_rm.domain.models;

enum class CharacterStatus {
    ALIVE, DEAD, UNKNOWN;

    companion object {
        fun fromString(status: String): CharacterStatus {
            return when (status.lowercase()) {
                "alive" -> ALIVE
                "dead" -> DEAD
                else -> UNKNOWN
            }
        }
    }
}