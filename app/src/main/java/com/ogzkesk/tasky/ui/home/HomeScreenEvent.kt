package com.ogzkesk.tasky.ui.home

import com.ogzkesk.database.mvi.ViewEvent

sealed interface HomeScreenEvent : ViewEvent {
    data class ToggleDropdownMenu(val value: Boolean) : HomeScreenEvent
    data class OnTabSelected(val tab: HomeScreenState.HomeTab) : HomeScreenEvent

    data class OnSortMethodChanged(val sortingMethod: HomeScreenState.SortingMethod) :
        HomeScreenEvent
}
