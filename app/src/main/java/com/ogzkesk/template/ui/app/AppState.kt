package com.ogzkesk.template.ui.app

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ogzkesk.data.network.NetworkStatusImpl
import com.ogzkesk.domain.network.NetworkStatus
import com.ogzkesk.template.navigation.AppContentRepository
import com.ogzkesk.template.navigation.NavigationEvent
import com.ogzkesk.ui.resource.AlertDialogData
import com.ogzkesk.ui.resource.BottomSheetData
import com.ogzkesk.ui.resource.SnackBarMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Stable
class AppState(
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    private val appContentRepository: AppContentRepository,
) {
    init {
        collectNavigationEvents()
    }

    @Composable
    fun getAlertDialogData(): AlertDialogData? {
        val data by appContentRepository.alertDialogEvent.collectAsStateWithLifecycle(
            initialValue = null,
        )
        return data
    }

    @Composable
    fun getLoadingIndicatorData(): Boolean {
        val data by appContentRepository.loadingIndicatorEvent.collectAsStateWithLifecycle(
            initialValue = false,
        )
        return data
    }

    @Composable
    fun getBottomSheetData(): BottomSheetData? {
        val data by appContentRepository.bottomSheetEvent.collectAsStateWithLifecycle(
            initialValue = null,
        )
        return data
    }

    @Composable
    fun getSnackBarData(): SnackBarMessage? {
        val data by appContentRepository.snackBarEvent.collectAsStateWithLifecycle(
            initialValue = null,
        )
        return data
    }

    @Composable
    fun getNetworkStatus(): NetworkStatus {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        return remember(context, lifecycleOwner) {
            NetworkStatusImpl(context, lifecycleOwner.lifecycle)
        }
    }

    private fun collectNavigationEvents() {
        coroutineScope.launch {
            appContentRepository.navigationEvent.collect {
                navigate(it)
            }
        }
    }

    private fun navigate(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.Back -> {
                navController.popBackStack()
            }

            is NavigationEvent.Navigate<*> -> {
                navController.navigate(navigationEvent.route) {
                    launchSingleTop = true
                }
            }

            is NavigationEvent.NavigateAndPop<*> -> {
                navController.run {
                    popBackStack()
                    navigate(navigationEvent.route) {
                        launchSingleTop = true
                    }
                }
            }
        }
        logBackQue()
    }

    @SuppressLint("RestrictedApi")
    private fun logBackQue() =
        Timber.d(
            message = "BackQueue: ${
                navController.currentBackStack.value.map { it.destination.route }
            }",
        )
}
