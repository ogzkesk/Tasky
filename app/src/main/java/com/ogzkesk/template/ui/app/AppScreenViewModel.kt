package com.ogzkesk.template.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.domain.util.MainDispatcher
import com.ogzkesk.template.navigation.AppContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppScreenViewModel @Inject constructor(
    val appContentRepository: AppContentRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {
    fun dismissAlertDialog() {
        viewModelScope.launch(mainDispatcher) {
            appContentRepository.updateAlertDialog(alertDialogData = null)
        }
    }

    fun dismissBottomSheet() {
        viewModelScope.launch(mainDispatcher) {
            appContentRepository.updateBottomSheet(bottomSheetData = null)
        }
    }

    fun dismissSnackBar() {
        viewModelScope.launch(mainDispatcher) {
            appContentRepository.updateSnackBar(message = null)
        }
    }
}
