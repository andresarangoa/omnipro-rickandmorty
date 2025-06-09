package com.app.omnipro_test_rm.ui.components.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.ui.components.DimensionErrorCard
import com.app.omnipro_test_rm.ui.components.EnhancedCharacterCard
import com.app.omnipro_test_rm.ui.components.PortalLoadingIndicator

@Composable
fun EnhancedCharactersList(
    characters: LazyPagingItems<CharacterRickAndMorty>,
    onCharacterClick: (String) -> Unit,
    onFavoriteClick: (CharacterRickAndMorty) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = characters.itemCount,
            key = characters.itemKey { it.id }
        ) { index ->
            val character = characters[index]
            if (character != null) {
                EnhancedCharacterCard(
                    character = character,
                    onClick = { onCharacterClick(character.id) },
                    onFavoriteClick = { onFavoriteClick(character) }
                )
            }
        }
        
        // Handle loading states
        when {
            characters.loadState.refresh is LoadState.Loading -> {
                item {
                    PortalLoadingIndicator()
                }
            }
            
            characters.loadState.append is LoadState.Loading -> {
                item {
                    PortalLoadingIndicator()
                }
            }
            
            characters.loadState.refresh is LoadState.Error -> {
                val error = characters.loadState.refresh as LoadState.Error
                item {
                    DimensionErrorCard(
                        message = error.error.message ?: "Portal malfunction detected!",
                        onRetry = { characters.retry() }
                    )
                }
            }
            
            characters.loadState.append is LoadState.Error -> {
                val error = characters.loadState.append as LoadState.Error
                item {
                    DimensionErrorCard(
                        message = error.error.message ?: "Portal connection lost!",
                        onRetry = { characters.retry() }
                    )
                }
            }
        }
    }
}