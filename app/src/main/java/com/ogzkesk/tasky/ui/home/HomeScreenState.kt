package com.ogzkesk.tasky.ui.home

import com.ogzkesk.domain.model.Task

data class HomeScreenState(
    val tasks: List<Task>? = null,
    val showDropdownMenu: Boolean = false,
    val sortingMethod: SortingMethod = SortingMethod.Default,
    val selectedTab: HomeTab = HomeTab.All,
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
