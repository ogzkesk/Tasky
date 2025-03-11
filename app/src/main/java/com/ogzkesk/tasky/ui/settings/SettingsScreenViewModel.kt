package com.ogzkesk.tasky.ui.settings

import androidx.lifecycle.viewModelScope
import com.ogzkesk.database.mvi.ViewModel
import com.ogzkesk.domain.prefs.PreferencesRepository
import com.ogzkesk.domain.util.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel<SettingsScreenState, SettingsScreenEvent>(SettingsScreenState()) {

    init {
        viewModelScope.launch(ioDispatcher) {
            preferencesRepository.getPreferences().collect { prefs ->
                updateState {
                    it.copy(currentTheme = prefs.theme)
                }
            }
        }
    }

    override fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.OnThemeSelected -> updateState { state ->
                viewModelScope.launch {
                    preferencesRepository.setPreferences {
                        it.copy(theme = event.theme)
                    }
                }
                state.copy(
                    currentTheme = event.theme,
                    themeDialogState = false
                )
            }

            is SettingsScreenEvent.ToggleThemeDialog -> updateState {
                it.copy(themeDialogState = event.value)
            }
        }
    }
}
