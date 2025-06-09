package com.app.omnipro_test_rm.ui.components.enhanced

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.domain.models.CharacterStatus
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@Composable
fun EnhancedCharacterHeader(character: CharacterRickAndMorty) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = character.name,
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center
                        )
                    }

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
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center, // ✅ Centrado explícito
                    modifier = Modifier.fillMaxWidth() // ✅ Ocupar todo el ancho
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), // ✅ Ocupar todo el ancho
                    horizontalArrangement = Arrangement.Center, // ✅ Centrado explícito
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

                    Spacer(modifier = Modifier.width(12.dp)) // ✅ Spacer explícito

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
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center, // ✅ Centrado explícito
                    modifier = Modifier.fillMaxWidth() // ✅ Ocupar todo el ancho
                )
            }
        }
    }
}