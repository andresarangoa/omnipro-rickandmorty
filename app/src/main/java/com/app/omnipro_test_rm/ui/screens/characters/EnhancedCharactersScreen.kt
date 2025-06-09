package com.app.omnipro_test_rm.ui.screens.characters

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.navigation3.runtime.NavKey
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.omnipro_test_rm.ui.components.ConnectivityAwareContent
import com.app.omnipro_test_rm.ui.theme.RickMortyColors
import org.koin.androidx.compose.koinViewModel
import com.app.omnipro_test_rm.ui.components.characters.EnhancedCharactersList
import com.app.omnipro_test_rm.ui.components.characters.MultiverseStatsSection
import com.app.omnipro_test_rm.ui.components.characters.RickMortyTopBar
import com.app.omnipro_test_rm.ui.components.utils.NodeTags.CHARACTERS_SCREEN_ROOT_TAG
import kotlinx.serialization.Serializable

@Serializable
data object CharacterScreen : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedCharactersScreen(
    onCharacterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val characters = uiState.characters.collectAsLazyPagingItems()
    val networkStatus by viewModel.networkStatus.collectAsState()

    // Animated background
    val infiniteTransition = rememberInfiniteTransition(label = "bg_animation")
    val backgroundAlpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bg_alpha"
    )

    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            viewModel.clearError()
        }
    }

    ConnectivityAwareContent(
        networkStatus = networkStatus,
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            RickMortyColors.PortalBlue.copy(alpha = backgroundAlpha)
                        )
                    )
                ).testTag(CHARACTERS_SCREEN_ROOT_TAG)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Enhanced App Bar
                RickMortyTopBar(
                    onRefreshClick = { characters.refresh() }
                )

                // Stats Section
                MultiverseStatsSection(characters = characters)

                // Characters List
                EnhancedCharactersList(
                    characters = characters,
                    onCharacterClick = onCharacterClick,
                    onFavoriteClick = { character ->
                        viewModel.toggleFavorite(character.id)
                    }
                )
            }
        }
    }
}