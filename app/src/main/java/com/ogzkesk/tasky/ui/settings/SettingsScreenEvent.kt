package com.ogzkesk.tasky.ui.settings

import com.ogzkesk.database.mvi.ViewEvent
import com.ogzkesk.domain.prefs.model.Theme

sealed interface SettingsScreenEvent : ViewEvent {
    data class ToggleThemeDialog(val value: Boolean) : SettingsScreenEvent
    data class OnThemeSelected(val theme: Theme) : SettingsScreenEvent
}