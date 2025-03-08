package com.ogzkesk.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.eventFlow
import androidx.lifecycle.repeatOnLifecycle
import com.ogzkesk.domain.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NetworkStatusImpl(
    context: Context,
    override val lifecycle: Lifecycle,
) : LifecycleOwner, NetworkStatus {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()
    private val mutableProxyStatus = MutableStateFlow(false)
    private val mutableVpnStatus = MutableStateFlow(false)
    private val mutableNetworkStatus = MutableStateFlow(false)

    override val vpnStatus: StateFlow<Boolean>
        get() = mutableProxyStatus
    override val proxyStatus: StateFlow<Boolean>
        get() = mutableVpnStatus
    override val hasActiveNetwork: StateFlow<Boolean>
        get() = mutableNetworkStatus

    init {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                lifecycle.eventFlow.collect {
                    mutableVpnStatus.emit(getVpnStatus())
                    mutableProxyStatus.emit(getProxyStatus())
                    mutableNetworkStatus.emit(getActiveNetwork())
                }
            }
        }
    }

    private fun getProxyStatus(): Boolean = connectivityManager?.defaultProxy != null

    private fun getVpnStatus(): Boolean =
        connectivityManager?.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                ?: false
        } ?: false

    private fun getActiveNetwork(): Boolean = connectivityManager?.activeNetwork != null
}
