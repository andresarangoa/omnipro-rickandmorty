package com.app.omnipro_test_rm.ui.screens.detail

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.app.omnipro_test_rm.R
import com.app.omnipro_test_rm.ui.components.ConnectivityAwareContent
import com.app.omnipro_test_rm.ui.components.DimensionErrorCard
import com.app.omnipro_test_rm.ui.components.enhanced.EnhancedCharacterDetailContent
import com.app.omnipro_test_rm.ui.components.PortalLoadingIndicator
import com.app.omnipro_test_rm.ui.components.utils.NodeTags.CHARACTER_DETAIL_SCREEN_ROOT_TAG
import com.app.omnipro_test_rm.ui.theme.RickMortyColors
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class CharacterDetail(val characterId: String) : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedCharacterDetailScreen(
    characterId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = koinViewModel { parametersOf(characterId) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val networkStatus by viewModel.networkStatus.collectAsState()

    // Animated background
    val infiniteTransition = rememberInfiniteTransition(label = "detail_bg")
    val backgroundAlpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "detail_bg_alpha"
    )

    ConnectivityAwareContent(
        networkStatus = networkStatus
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.character_profile),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to multiverse",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    actions = {
                        uiState.characterRickAndMorty?.let { character ->
                            IconButton(onClick = { viewModel.toggleFavorite() }) {
                                Icon(
                                    imageVector = if (character.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = if (character.isFavorite) stringResource(R.string.remove_from_favorites) else stringResource(
                                        R.string.add_to_favorites
                                    ),
                                    tint = if (character.isFavorite) RickMortyColors.DeadRed else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                RickMortyColors.PortalBlue.copy(alpha = backgroundAlpha)
                            )
                        )
                    ).testTag(CHARACTER_DETAIL_SCREEN_ROOT_TAG)
            ) {
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            PortalLoadingIndicator()
                        }
                    }

                    uiState.error != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            DimensionErrorCard(
                                message = uiState.error!!,
                                onRetry = { viewModel.clearError() }
                            )
                        }
                    }

                    uiState.characterRickAndMorty != null -> {
                        EnhancedCharacterDetailContent(
                            character = uiState.characterRickAndMorty!!,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}