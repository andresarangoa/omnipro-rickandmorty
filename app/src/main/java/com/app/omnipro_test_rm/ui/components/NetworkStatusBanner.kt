package com.app.omnipro_test_rm.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.omnipro_test_rm.R
import com.app.omnipro_test_rm.data.network.ConnectivityObserver
import com.app.omnipro_test_rm.ui.theme.RickMortyColors

@Composable
fun NetworkStatusBanner(
    networkStatus: ConnectivityObserver.Status,
    modifier: Modifier = Modifier
) {
    val isConnected = networkStatus == ConnectivityObserver.Status.Available
    
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(300)
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(300)
        ),
        modifier = modifier
    ) {
        Surface(
            color = if (isConnected) RickMortyColors.AliveGreen else RickMortyColors.DeadRed,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = when (networkStatus) {
                        ConnectivityObserver.Status.Available -> painterResource(R.drawable.ic_wifi)
                        ConnectivityObserver.Status.Losing -> painterResource(R.drawable.ic_wifi_off)
                        ConnectivityObserver.Status.Lost,
                        ConnectivityObserver.Status.Unavailable -> painterResource(R.drawable.ic_cloud_off)
                    },
                    contentDescription = when (networkStatus) {
                        ConnectivityObserver.Status.Available -> stringResource(R.string.cd_wifi_icon)
                        ConnectivityObserver.Status.Losing -> stringResource(R.string.cd_wifi_off_icon)
                        ConnectivityObserver.Status.Lost,
                        ConnectivityObserver.Status.Unavailable -> stringResource(R.string.cd_cloud_off_icon)
                    },
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = when (networkStatus) {
                        ConnectivityObserver.Status.Available -> stringResource(R.string.network_connected)
                        ConnectivityObserver.Status.Losing -> stringResource(R.string.network_losing)
                        ConnectivityObserver.Status.Lost -> stringResource(R.string.network_lost)
                        ConnectivityObserver.Status.Unavailable -> stringResource(R.string.network_unavailable)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}