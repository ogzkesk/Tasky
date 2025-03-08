package com.ogzkesk.template.navigation

import com.ogzkesk.ui.resource.AlertDialogData
import com.ogzkesk.ui.resource.BottomSheetData
import com.ogzkesk.ui.resource.SnackBarMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppContentRepository @Inject constructor() {
    private val _navigationEvent: Channel<NavigationEvent> = Channel()
    private val _snackBarEvent: Channel<SnackBarMessage?> = Channel()
    private val _bottomSheetEvent: Channel<BottomSheetData?> = Channel()
    private val _alertDialogEvent: Channel<AlertDialogData?> = Channel()
    private val _loadingIndicatorEvent: Channel<Boolean> = Channel()

    val navigationEvent: Flow<NavigationEvent> = _navigationEvent.receiveAsFlow()
    val snackBarEvent: Flow<SnackBarMessage?> = _snackBarEvent.receiveAsFlow()
    val bottomSheetEvent: Flow<BottomSheetData?> = _bottomSheetEvent.receiveAsFlow()
    val alertDialogEvent: Flow<AlertDialogData?> = _alertDialogEvent.receiveAsFlow()
    val loadingIndicatorEvent: Flow<Boolean> = _loadingIndicatorEvent.receiveAsFlow()

    suspend fun updateNavigation(event: NavigationEvent) = _navigationEvent.send(event)

    suspend fun updateBottomSheet(bottomSheetData: BottomSheetData?) = _bottomSheetEvent.send(bottomSheetData)

    suspend fun updateAlertDialog(alertDialogData: AlertDialogData?) = _alertDialogEvent.send(alertDialogData)

    suspend fun updateLoadingIndicator(state: Boolean) = _loadingIndicatorEvent.send(state)

    suspend fun updateSnackBar(message: SnackBarMessage?) = _snackBarEvent.send(message)
}
