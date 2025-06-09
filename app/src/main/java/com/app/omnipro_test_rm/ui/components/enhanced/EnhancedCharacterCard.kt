package com.app.omnipro_test_rm.ui.components.enhanced

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.domain.models.CharacterStatus
import com.app.omnipro_test_rm.ui.components.StatusChip
import com.app.omnipro_test_rm.ui.components.StatusIndicator
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@Composable
fun EnhancedCharacterCard(
    character: CharacterRickAndMorty,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { 
                isPressed = true
                onClick()
            },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Character Avatar with Portal Effect
            Box {
                // Portal glow effect
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    RickMortyColors.PortalBlue.copy(alpha = 0.3f),
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
                        .size(64.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
                
                // Enhanced Status indicator
                StatusIndicator(
                    status = character.status,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Character Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Status and Species with styled chips
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusChip(
                        text = character.status.name.lowercase().replaceFirstChar { it.uppercase() },
                        color = when (character.status) {
                            CharacterStatus.ALIVE -> RickMortyColors.AliveGreen
                            CharacterStatus.DEAD -> RickMortyColors.DeadRed
                            CharacterStatus.UNKNOWN -> RickMortyColors.UnknownGray
                        }
                    )
                    
                    Text(
                        text = character.species,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                if (character.location?.name?.isNotBlank() == true) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "📍 ${character.location.name}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            // Animated Favorite Button
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (character.isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (character.isFavorite) RickMortyColors.DeadRed else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
    
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}
