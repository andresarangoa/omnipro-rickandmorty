package com.app.omnipro_test_rm.ui.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty

data class CharactersUiState(
    val characters: Flow<PagingData<CharacterRickAndMorty>> = emptyFlow(),
    val favoriteCharacterRickAndMorties: List<CharacterRickAndMorty> = emptyList(),
    val isShowingFavorites: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)