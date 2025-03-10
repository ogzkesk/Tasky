package com.ogzkesk.tasky.ui.home

import com.ogzkesk.database.mvi.ViewEvent
import com.ogzkesk.domain.model.Task

sealed interface HomeScreenEvent : ViewEvent {
    data class ToggleDropdownMenu(val value: Boolean) : HomeScreenEvent
    data class OnTabSelected(val tab: HomeScreenState.HomeTab) : HomeScreenEvent

    data class OnSortMethodChanged(val sortingMethod: HomeScreenState.SortingMethod) :
        HomeScreenEvent

    data class OnTasksReordered(val tasks: List<Task>) : HomeScreenEvent
    data class CompleteTask(val task: Task) :
        HomeScreenEvent

    data class RemoveTask(val task: Task) : HomeScreenEvent
}
