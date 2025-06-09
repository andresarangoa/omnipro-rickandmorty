package com.app.omnipro_test_rm.ui.components.enhanced

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.omnipro_test_rm.R
import com.app.omnipro_test_rm.domain.models.Episode
import com.app.omnipro_test_rm.ui.components.pluralString
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@Composable
fun EnhancedEpisodesCard(
    episodes: List<Episode>,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(600)
        isVisible = true
    }
    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600),
        label = "episodes_alpha"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { this.alpha = alpha },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_tv),
                    contentDescription = stringResource(R.string.cd_tv_icon),
                    tint = RickMortyColors.Accent,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.multiverse_adventures),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = RickMortyColors.Accent
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Episodes count banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = RickMortyColors.Accent.copy(alpha = 0.1f)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "📺",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Column {
                        Text(
                            text = pluralString(R.plurals.episodes_count, episodes.size, episodes.size),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RickMortyColors.Accent
                        )
                        Text(
                            text = stringResource(R.string.interdimensional_appearances),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Show first 5 episodes
            episodes.take(5).forEachIndexed { index, episode ->
                EnhancedEpisodeItem(
                    episode = episode,
                    index = index
                )
                if (index < 4 && index < episodes.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            // Show more episodes indicator
            if (episodes.size > 5) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = pluralString(R.plurals.more_adventures, episodes.size - 5, episodes.size - 5),
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}