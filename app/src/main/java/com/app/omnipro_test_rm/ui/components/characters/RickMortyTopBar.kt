package com.app.omnipro_test_rm.ui.components.characters

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.omnipro_test_rm.R
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickMortyTopBar(
    onRefreshClick: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_galaxya_off),
                        contentDescription = null,
                        tint = RickMortyColors.PortalBlue,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Rick & Morty",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = stringResource(id = R.string.app_tagline),
                    style = MaterialTheme.typography.bodyMedium,
                    color = RickMortyColors.PortalGreen,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        actions = {
            IconButton(onClick = onRefreshClick) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    )
}