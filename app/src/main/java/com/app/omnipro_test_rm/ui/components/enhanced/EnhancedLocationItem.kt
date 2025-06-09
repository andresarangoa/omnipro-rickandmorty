package com.app.omnipro_test_rm.ui.components.enhanced

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.omnipro_test_rm.R
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@Composable
fun EnhancedLocationItem(
    title: String,
    locationName: String,
    locationType: String?,
    dimension: String?,
    isHighlighted: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = if (isHighlighted) {
            RickMortyColors.PortalBlue.copy(alpha = 0.1f)
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = if (isHighlighted) {
                    RickMortyColors.PortalBlue
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = locationName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            if (!locationType.isNullOrBlank() || !dimension.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                val locationText = when {
                    !locationType.isNullOrBlank() && !dimension.isNullOrBlank() -> {
                        stringResource(R.string.location_type_dimension, locationType, dimension)
                    }
                    !locationType.isNullOrBlank() -> {
                        stringResource(R.string.location_type_only, locationType)
                    }
                    !dimension.isNullOrBlank() -> {
                        stringResource(R.string.location_dimension_only, dimension)
                    }
                    else -> ""
                }
                
                if (locationText.isNotEmpty()) {
                    Text(
                        text = locationText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}