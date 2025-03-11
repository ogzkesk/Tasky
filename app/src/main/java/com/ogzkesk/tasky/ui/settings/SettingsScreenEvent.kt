package com.ogzkesk.tasky.ui.settings

import androidx.compose.ui.graphics.Color
import com.ogzkesk.database.mvi.ViewEvent
import com.ogzkesk.domain.prefs.model.Theme

sealed interface SettingsScreenEvent : ViewEvent {
    data class ToggleThemeDialog(val value: Boolean) : SettingsScreenEvent
    data class ToggleColorPickerDialog(val value: Boolean) : SettingsScreenEvent
    data class OnThemeSelected(val mode: Theme.Mode) : SettingsScreenEvent
    data class OnDynamicColorChanged(val dynamicColor: Boolean) : SettingsScreenEvent
    data class OnPrimaryColorSelected(val color: Color) : SettingsScreenEvent
}