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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.omnipro_test_rm.R
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.ui.theme.RickMortyColors


@Composable
fun EnhancedLocationsCard(
    character: CharacterRickAndMorty,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(400)
        isVisible = true
    }
    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600),
        label = "locations_alpha"
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
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.cd_location_icon),
                    tint = RickMortyColors.PortalGreen,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.dimensional_locations),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = RickMortyColors.PortalGreen
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Origin Location
            character.origin?.let { origin ->
                EnhancedLocationItem(
                    title = stringResource(R.string.origin_location),
                    locationName = origin.name,
                    locationType = origin.type,
                    dimension = origin.dimension,
                    isHighlighted = true
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Current Location
            character.location?.let { location ->
                EnhancedLocationItem(
                    title = stringResource(R.string.current_location),
                    locationName = location.name,
                    locationType = location.type,
                    dimension = location.dimension,
                    isHighlighted = false
                )
            }
        }
    }
}