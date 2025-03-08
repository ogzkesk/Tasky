package com.ogzkesk.tasky.ui.home

import com.ogzkesk.database.mvi.ViewModel
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(

): ViewModel<HomeScreenState,HomeScreenEvent>(HomeScreenState()) {
    override fun onEvent(event: HomeScreenEvent) {

    }
}
