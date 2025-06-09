package com.app.omnipro_test_rm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originId: String?,
    val originName: String?,
    val originType: String?,
    val originDimension: String?,
    val locationId: String?,
    val locationName: String?,
    val locationType: String?,
    val locationDimension: String?,
    val image: String,
    val created: String,
    val episodeCount: Int,
    val isFavorite: Boolean = false
)