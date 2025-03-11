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
                    preferencesRepository.setPreferences { prefs ->
                        prefs.copy(theme = prefs.theme.copy(mode = event.mode))
                    }
                }
                state.copy(
                    currentTheme = state.currentTheme.copy(mode = event.mode),
                    themeDialogState = false
                )
            }

            is SettingsScreenEvent.ToggleThemeDialog -> updateState {
                it.copy(themeDialogState = event.value)
            }

            is SettingsScreenEvent.OnDynamicColorChanged -> {
                viewModelScope.launch {
                    preferencesRepository.setPreferences { prefs ->
                        prefs.copy(theme = prefs.theme.copy(dynamicColor = event.dynamicColor))
                    }
                }
            }

            is SettingsScreenEvent.OnPrimaryColorSelected -> updateState { state ->
                viewModelScope.launch {
                    preferencesRepository.setPreferences { prefs ->
                        prefs.copy(theme = prefs.theme.copy(primaryColorHex = event.color.value))
                    }
                }
                state.copy(colorPickerDialogState = false)
            }

            is SettingsScreenEvent.ToggleColorPickerDialog -> updateState {
                it.copy(colorPickerDialogState = event.value)
            }
        }
    }
}
