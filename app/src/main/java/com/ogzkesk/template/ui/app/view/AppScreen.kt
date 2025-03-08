package com.ogzkesk.template.ui.app.view

import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ogzkesk.template.navigation.rememberAppState
import com.ogzkesk.template.ui.app.AppScreenViewModel
import com.ogzkesk.template.ui.app.AppState
import com.ogzkesk.template.ui.sheet.BottomSheetContainer
import com.ogzkesk.ui.composable.CustomAlertDialog
import com.ogzkesk.ui.composable.CustomLoadingIndicator
import com.ogzkesk.ui.composable.CustomSnackBar
import timber.log.Timber

@Composable
fun AppScreen(viewModel: AppScreenViewModel = hiltViewModel()) {
    val appState: AppState = rememberAppState(
        appContentRepository = viewModel.appContentRepository,
    )
    val networkStatus = appState.getNetworkStatus()
    val vpnStatus by networkStatus.vpnStatus.collectAsStateWithLifecycle()
    val proxyStatus by networkStatus.proxyStatus.collectAsStateWithLifecycle()
    LaunchedEffect(vpnStatus, proxyStatus) {
        if (vpnStatus || proxyStatus) {
            Timber.i("proxyStatus: $proxyStatus, vpnStatus: $vpnStatus")
        }
    }

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Bottom),
    ) { paddingValues ->
        RootNavHost(appState = appState)

        CustomAlertDialog(
            data = appState.getAlertDialogData(),
            onConfirm = viewModel::dismissAlertDialog,
        )

        CustomSnackBar(
            data = appState.getSnackBarData(),
            onDismiss = viewModel::dismissSnackBar,
        )

        CustomLoadingIndicator(
            enabled = appState.getLoadingIndicatorData(),
        )

        BottomSheetContainer(
            modifier = Modifier.padding(paddingValues),
            data = appState.getBottomSheetData(),
            onDismiss = viewModel::dismissBottomSheet,
        )
    }
}
