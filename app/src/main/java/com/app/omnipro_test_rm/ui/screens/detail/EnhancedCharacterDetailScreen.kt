package com.app.omnipro_test_rm.ui.screens.detail
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import coil.compose.AsyncImage
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.domain.models.CharacterStatus
import com.app.omnipro_test_rm.ui.components.DimensionErrorCard
import com.app.omnipro_test_rm.ui.components.PortalLoadingIndicator
import com.app.omnipro_test_rm.ui.theme.RickMortyColors
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class CharacterDetail(val characterId: String): NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedCharacterDetailScreen(
    characterId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = koinViewModel { parametersOf(characterId) }
) {
    val uiState by viewModel.uiState.collectAsState()
    
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
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Character Profile",
                        color = MaterialTheme.colorScheme.onSurface
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                                contentDescription = if (character.isFavorite) "Remove from favorites" else "Add to favorites",
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
                )
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

@Composable
private fun EnhancedCharacterDetailContent(
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
//            EnhancedBasicInfoCard(character = character)
        }
        
        item {
//            EnhancedLocationsCard(character = character)
        }
        
        if (character.episodes.isNotEmpty()) {
            item {
//                EnhancedEpisodesCard(episodes = character.episodes)
            }
        }
    }
}

@Composable
private fun EnhancedCharacterHeader(character: CharacterRickAndMorty) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        isVisible = true
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Portal effect around avatar
            Box(
                contentAlignment = Alignment.Center
            ) {
                // Outer glow
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    RickMortyColors.PortalBlue.copy(alpha = 0.3f),
                                    RickMortyColors.PortalGreen.copy(alpha = 0.2f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
                
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                // Enhanced status indicator
                EnhancedStatusIndicator(
                    status = character.status,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 8.dp, y = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EnhancedStatusChip(
                    text = character.status.name.lowercase().replaceFirstChar { it.uppercase() },
                    color = when (character.status) {
                        CharacterStatus.ALIVE -> RickMortyColors.AliveGreen
                        CharacterStatus.DEAD -> RickMortyColors.DeadRed
                        CharacterStatus.UNKNOWN -> RickMortyColors.UnknownGray
                    }
                )
                
                EnhancedStatusChip(
                    text = character.species,
                    color = RickMortyColors.ScienceBlue
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "🧬 Interdimensional Entity",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun EnhancedStatusChip(
    text: String,
    color: Color
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EnhancedStatusIndicator(
    status: CharacterStatus,
    modifier: Modifier = Modifier
) {
    val color = when (status) {
        CharacterStatus.ALIVE -> RickMortyColors.AliveGreen
        CharacterStatus.DEAD -> RickMortyColors.DeadRed
        CharacterStatus.UNKNOWN -> RickMortyColors.UnknownGray
    }
    
    Box(
        modifier = modifier
            .size(28.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .padding(4.dp)
            .background(
                color = color,
                shape = CircleShape
            )
    )
}
