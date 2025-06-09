package com.app.omnipro_test_rm.domain.usecases

import com.app.omnipro_test_rm.data.network.ConnectivityObserver
import kotlinx.coroutines.flow.Flow

class ObserveConnectivityUseCase(
    private val connectivityObserver: ConnectivityObserver
) {
    operator fun invoke(): Flow<ConnectivityObserver.Status> {
        return connectivityObserver.observe()
    }
}