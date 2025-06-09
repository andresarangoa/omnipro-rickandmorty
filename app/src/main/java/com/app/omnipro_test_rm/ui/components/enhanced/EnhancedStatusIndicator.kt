package com.app.omnipro_test_rm.ui.components.enhanced

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.omnipro_test_rm.domain.models.CharacterStatus
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@Composable
fun EnhancedStatusIndicator(
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