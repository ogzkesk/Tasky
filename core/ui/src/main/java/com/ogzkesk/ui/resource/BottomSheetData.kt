package com.ogzkesk.ui.resource

import com.ogzkesk.domain.prefs.model.Theme

sealed class BottomSheetData {
    data class Data1(
        val message: String,
        val onConfirm: () -> Unit,
    ) : BottomSheetData()

    data class ThemeData(
        val onThemeSelected: (Theme) -> Unit,
    ) : BottomSheetData()
}
