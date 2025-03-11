package com.ogzkesk.tasky.ui.home

import com.ogzkesk.database.mvi.ViewEvent
import com.ogzkesk.domain.model.Task

sealed interface HomeScreenEvent : ViewEvent {
    data object InsertTestData : HomeScreenEvent
    data object ClearTasks : HomeScreenEvent
    data class NavigateToSettings(val value: Boolean) : HomeScreenEvent
    data class ToggleDropdownMenu(val value: Boolean) : HomeScreenEvent
    data class ToggleMenu(val value: Boolean) : HomeScreenEvent
    data class ToggleSpotlight(val showed: Boolean) : HomeScreenEvent
    data class ToggleClearDialog(val value: Boolean) : HomeScreenEvent
    data class OnTabSelected(val tab: HomeScreenState.HomeTab) : HomeScreenEvent
    data class OnTasksReordered(val tasks: List<Task>) : HomeScreenEvent
    data class CompleteTask(val task: Task) : HomeScreenEvent
    data class UndoCompletedTask(val task: Task) : HomeScreenEvent
    data class RemoveTask(val task: Task) : HomeScreenEvent
    data class UndoRemovedTask(val task: Task) : HomeScreenEvent
    data class OnSortMethodChanged(val sortingMethod: HomeScreenState.SortingMethod) :
        HomeScreenEvent
}
