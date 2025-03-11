package com.ogzkesk.tasky.ui.home

import com.ogzkesk.domain.model.Task

data class HomeScreenState(
    val tasks: List<Task>? = null,
    val showSortDropdown: Boolean = false,
    val showMenu: Boolean = false,
    val sortingMethod: SortingMethod = SortingMethod.Default,
    val selectedTab: HomeTab = HomeTab.All,
    val clearDialogState: Boolean = false,
    val isTasksEmpty: Boolean = false,
    val isSpotlightShowed: Boolean = false,
    val navigateToSettings: Boolean = false,
) {
    val completedTasks: List<Task>?
        get() = tasks?.filter { it.isCompleted }

    val pendingTasks: List<Task>?
        get() = tasks?.filter { !it.isCompleted }

    enum class HomeTab {
        All,
        Pending,
        Completed;
    }

    enum class SortingMethod {
        Default,
        Priority,
        Date,
        Alphabetically;
    }
}
