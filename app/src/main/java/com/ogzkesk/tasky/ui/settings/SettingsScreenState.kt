package com.ogzkesk.tasky.ui.settings

import com.ogzkesk.domain.prefs.model.Theme

data class SettingsScreenState(
    val themeDialogState: Boolean = false,
    val currentTheme: Theme = Theme.System,
)
