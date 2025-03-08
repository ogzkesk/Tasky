package com.ogzkesk.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.ogzkesk.network.exception.ConnectionException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectionInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (context.getSystemService<ConnectivityManager>()?.activeNetwork == null) {
            throw ConnectionException("No internet connection")
        }
        return chain.proceed(chain.request())
    }
}
