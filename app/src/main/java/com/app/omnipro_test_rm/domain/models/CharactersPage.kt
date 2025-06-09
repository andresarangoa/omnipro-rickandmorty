package com.app.omnipro_test_rm.domain.models

data class CharactersPage(
    val characterRickAndMorties: List<CharacterRickAndMorty>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNextPage: Boolean
)