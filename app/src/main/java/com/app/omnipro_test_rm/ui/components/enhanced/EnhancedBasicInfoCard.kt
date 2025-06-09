package com.app.omnipro_test_rm.ui.components.enhanced

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.ui.components.InfoRowWithIcon
import com.app.omnipro_test_rm.ui.components.pluralString
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@Composable
fun EnhancedBasicInfoCard(
    character: CharacterRickAndMorty,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(200)
        isVisible = true
    }
    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600),
        label = "basic_info_alpha"
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
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.cd_info_icon),
                    tint = RickMortyColors.ScienceBlue,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.basic_information),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = RickMortyColors.ScienceBlue
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Enhanced Info Rows
            InfoRowWithIcon(
                icon = painterResource(R.drawable.ic_person),
                label = stringResource(R.string.label_gender),
                value = character.gender,
                iconColor = RickMortyColors.PortalGreen
            )
            
            InfoRowWithIcon(
                icon = painterResource(R.drawable.ic_tv),
                label = stringResource(R.string.label_episodes),
                value = pluralString(R.plurals.episodes_appearances, character.episodeCount, character.episodeCount),
                iconColor = RickMortyColors.Accent
            )
            
            InfoRowWithIcon(
                icon = painterResource(R.drawable.ic_people),
                label = stringResource(R.string.label_dimension),
                value = character.origin?.dimension ?: stringResource(R.string.unknown_dimension),
                iconColor = RickMortyColors.PortalBlue
            )
            
            InfoRowWithIcon(
                icon = painterResource(R.drawable.ic_science),
                label = stringResource(R.string.label_type),
                value = character.type.ifBlank { 
                    stringResource(R.string.standard_species, character.species) 
                },
                iconColor = RickMortyColors.DeadRed
            )
        }
    }
}