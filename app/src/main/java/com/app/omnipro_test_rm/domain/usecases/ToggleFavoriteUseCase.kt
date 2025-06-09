package com.app.omnipro_test_rm.domain.usecases

import com.app.omnipro_test_rm.domain.repository.ICharacterRepository

class ToggleFavoriteUseCase(private val repository: ICharacterRepository) {
    suspend operator fun invoke(id: String) {
        repository.toggleFavorite(id)
    }
}