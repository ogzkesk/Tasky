package com.ogzkesk.tasky.ui.home

import com.ogzkesk.database.mvi.ViewEvent

sealed interface HomeScreenEvent : ViewEvent{
    data object ShowDropdownMenu : HomeScreenEvent
}