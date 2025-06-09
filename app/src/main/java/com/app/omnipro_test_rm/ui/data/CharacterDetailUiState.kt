package com.app.omnipro_test_rm.ui.data

import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty

data class CharacterDetailUiState(
    val characterRickAndMorty: CharacterRickAndMorty? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)