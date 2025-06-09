package com.app.omnipro_test_rm.domain.models

data class CharacterRickAndMorty(
    val id: String,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location?,
    val location: Location?,
    val image: String,
    val episodes: List<Episode>,
    val episodeCount: Int,
    val created: String,
    val isFavorite: Boolean = false
)
