package com.app.omnipro_test_rm.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty

interface ICharacterRepository {
    fun getCharactersPaged(): Flow<PagingData<CharacterRickAndMorty>>
    suspend fun getCharacterById(id: String): Result<CharacterRickAndMorty>
    suspend fun toggleFavorite(id: String)
    fun getFavoriteCharacters(): Flow<List<CharacterRickAndMorty>>
}
