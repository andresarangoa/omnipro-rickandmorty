package com.app.omnipro_test_rm.ui.components.enhanced

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty


@Composable
fun EnhancedCharacterDetailContent(
    character: CharacterRickAndMorty,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            EnhancedCharacterHeader(character = character)
        }
        
        item {
            EnhancedBasicInfoCard(character = character)
        }
        
        item {
            EnhancedLocationsCard(character = character)
        }
        
        if (character.episodes.isNotEmpty()) {
            item {
                EnhancedEpisodesCard(episodes = character.episodes)
            }
        }
    }
}