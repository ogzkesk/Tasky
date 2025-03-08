package com.ogzkesk.domain.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkStatus {
    val vpnStatus: StateFlow<Boolean>
    val proxyStatus: StateFlow<Boolean>
    val hasActiveNetwork: StateFlow<Boolean>
}
