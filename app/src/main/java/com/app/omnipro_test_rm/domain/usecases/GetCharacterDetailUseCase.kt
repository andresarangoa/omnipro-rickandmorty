package com.app.omnipro_test_rm.domain.usecases

import com.app.omnipro_test_rm.domain.repository.ICharacterRepository
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty

class GetCharacterDetailUseCase(private val repository: ICharacterRepository) {
    suspend operator fun invoke(id: String): Result<CharacterRickAndMorty> {
        return repository.getCharacterById(id)
    }
}