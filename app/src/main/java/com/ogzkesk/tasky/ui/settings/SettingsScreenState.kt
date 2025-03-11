package com.ogzkesk.tasky.ui.settings

import androidx.compose.ui.graphics.Color
import com.ogzkesk.domain.prefs.model.Theme

data class SettingsScreenState(
    val themeDialogState: Boolean = false,
    val colorPickerDialogState: Boolean = false,
    val currentTheme: Theme = Theme.DEFAULT,
    val currentPrimaryColor: Color? = null,
)
