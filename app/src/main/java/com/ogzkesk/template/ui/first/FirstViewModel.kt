package com.ogzkesk.template.ui.first

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.domain.prefs.PreferencesRepository
import com.ogzkesk.template.navigation.AppContentRepository
import com.ogzkesk.template.navigation.NavigationEvent
import com.ogzkesk.template.navigation.SecondScreenRoute
import com.ogzkesk.ui.resource.AlertDialogData
import com.ogzkesk.ui.resource.BottomSheetData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val appContentRepository: AppContentRepository,
) : ViewModel() {
    fun showThemeBottomSheet() {
        viewModelScope.launch {
            appContentRepository.updateBottomSheet(
                BottomSheetData.ThemeData { selectedTheme ->
                    viewModelScope.launch {
                        preferencesRepository.setPreferences {
                            it.copy(theme = selectedTheme)
                        }
                    }
                },
            )
        }
    }

    fun navigateToSecondScreen() {
        viewModelScope.launch {
            appContentRepository.updateNavigation(
                NavigationEvent.Navigate(
                    SecondScreenRoute(""),
                ),
            )
        }
    }

    fun updateDialog(data: AlertDialogData) {
        viewModelScope.launch {
            appContentRepository.updateAlertDialog(data)
        }
    }

    fun updateBottomSheet(data: BottomSheetData) {
        viewModelScope.launch {
            appContentRepository.updateBottomSheet(data)
        }
    }
}
