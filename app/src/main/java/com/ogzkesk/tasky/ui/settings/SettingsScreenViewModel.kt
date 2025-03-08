package com.ogzkesk.tasky.ui.settings

import com.ogzkesk.database.mvi.ViewModel
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor(

): ViewModel<SettingsScreenState,SettingsScreenEvent>(SettingsScreenState()) {
    override fun onEvent(event: SettingsScreenEvent) {

    }
}
