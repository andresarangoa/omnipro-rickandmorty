package com.app.omnipro_test_rm.domain.usecases

import androidx.paging.PagingData
import com.app.omnipro_test_rm.domain.repository.ICharacterRepository
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(private val repository: ICharacterRepository) {
    operator fun invoke(): Flow<PagingData<CharacterRickAndMorty>> {
        return repository.getCharactersPaged()
    }
}