package com.app.omnipro_test_rm.ui.components.characters

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.app.omnipro_test_rm.R
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.ui.components.enhanced.EnhancedStatsCard
import com.app.omnipro_test_rm.ui.theme.RickMortyColors
import java.util.Locale

@Composable
fun MultiverseStatsSection(characters: LazyPagingItems<CharacterRickAndMorty>) {
    val loadedCharacters = characters.itemSnapshotList.items
    val favoriteCount = loadedCharacters.count { it.isFavorite }
    
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(300)
        isVisible = true
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.all_characters),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${loadedCharacters.size} ${stringResource(id = R.string.entities_discover)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = RickMortyColors.DeadRed.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "❤️",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${stringResource(id = R.string.favorites)} ($favoriteCount)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = RickMortyColors.DeadRed,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Enhanced stats grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EnhancedStatsCard(
                    title = stringResource(id = R.string.characters).uppercase(Locale.getDefault()),
                    count = 826,
                    color = RickMortyColors.ScienceBlue,
                    icon = "👥",
                    modifier = Modifier.weight(1f)
                )
                
                EnhancedStatsCard(
                    title = stringResource(id = R.string.episodes).uppercase(Locale.getDefault()),
                    count = 51,
                    color = RickMortyColors.PortalGreen,
                    icon = "📺",
                    modifier = Modifier.weight(1f)
                )
                
                EnhancedStatsCard(
                    title = stringResource(id = R.string.locations).uppercase(Locale.getDefault()),
                    count = 126,
                    color = RickMortyColors.Accent,
                    icon = "🌍",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
