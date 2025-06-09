package com.app.omnipro_test_rm.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.app.omnipro_test_rm.domain.usecases.GetCharactersUseCase
import com.app.omnipro_test_rm.domain.usecases.ToggleFavoriteUseCase
import com.app.omnipro_test_rm.ui.data.CharactersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()
    
    init {
        loadCharacters()
    }
    
    private fun loadCharacters() {
        val charactersFlow = getCharactersUseCase().cachedIn(viewModelScope)
        _uiState.value = _uiState.value.copy(characters = charactersFlow)
    }
    
    fun toggleFavorite(characterId: String) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(characterId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}