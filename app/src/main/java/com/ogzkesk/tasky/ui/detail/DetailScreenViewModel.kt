package com.ogzkesk.tasky.ui.detail

import com.ogzkesk.database.mvi.ViewModel
import javax.inject.Inject

class DetailScreenViewModel @Inject constructor(

): ViewModel<DetailScreenState,DetailScreenEvent>(DetailScreenState()) {
    override fun onEvent(event: DetailScreenEvent) {

    }
}
