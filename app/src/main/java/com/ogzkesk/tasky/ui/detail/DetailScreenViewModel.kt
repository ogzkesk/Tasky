package com.ogzkesk.tasky.ui.detail

import com.ogzkesk.database.mvi.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(

): ViewModel<DetailScreenState,DetailScreenEvent>(DetailScreenState()) {
    override fun onEvent(event: DetailScreenEvent) {

    }
}
