package com.ogzkesk.tasky.ui.creation

import com.ogzkesk.database.mvi.ViewModel
import javax.inject.Inject

class CreationScreenViewModel @Inject constructor(

): ViewModel<CreationScreenState,CreationScreenEvent>(CreationScreenState()) {
    override fun onEvent(event: CreationScreenEvent) {

    }
}
