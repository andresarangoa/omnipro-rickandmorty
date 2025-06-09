package com.app.omnipro_test_rm.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.omnipro_test_rm.data.network.ConnectivityObserver
import com.app.omnipro_test_rm.domain.usecases.GetCharacterDetailUseCase
import com.app.omnipro_test_rm.domain.usecases.ObserveConnectivityUseCase
import com.app.omnipro_test_rm.domain.usecases.ToggleFavoriteUseCase
import com.app.omnipro_test_rm.ui.data.CharacterDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterId: String,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val observeConnectivityUseCase: ObserveConnectivityUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    val networkStatus: StateFlow<ConnectivityObserver.Status> =
        observeConnectivityUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ConnectivityObserver.Status.Available
            )

    init {
        loadCharacter()
    }
    
    private fun loadCharacter() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            getCharacterDetailUseCase(characterId).fold(
                onSuccess = { character ->
                    _uiState.value = _uiState.value.copy(
                        characterRickAndMorty = character,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        error = exception.message,
                        isLoading = false
                    )
                }
            )
        }
    }
    
    fun toggleFavorite() {
        viewModelScope.launch {
            _uiState.value.characterRickAndMorty?.let { character ->
                try {
                    toggleFavoriteUseCase(character.id)
                    _uiState.value = _uiState.value.copy(
                        characterRickAndMorty = character.copy(isFavorite = !character.isFavorite)
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(error = e.message)
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}