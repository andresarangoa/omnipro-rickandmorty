package com.app.omnipro_test_rm.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.app.omnipro_test_rm.data.network.ConnectivityObserver

@Composable
fun ConnectivityAwareContent(
    networkStatus: ConnectivityObserver.Status,
    content: @Composable (ConnectivityObserver.Status) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        NetworkStatusBanner(networkStatus = networkStatus)
        content(networkStatus)
    }
}