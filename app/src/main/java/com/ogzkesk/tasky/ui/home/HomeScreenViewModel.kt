package com.ogzkesk.tasky.ui.home

import androidx.lifecycle.viewModelScope
import com.ogzkesk.database.mvi.ViewModel
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.model.Task
import com.ogzkesk.domain.task.TaskRepository
import com.ogzkesk.domain.util.IoDispatcher
import com.ogzkesk.tasky.ui.home.HomeScreenState.SortingMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val logger: Logger,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel<HomeScreenState, HomeScreenEvent>(HomeScreenState()) {

    init {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.stream().collect { tasks ->
                updateState { state ->
                    val sorted = tasks.ifEmpty { null }?.let {
                        sort(it, state.sortingMethod)
                    }
                    state.copy(
                        tasks = sorted,
                        isTasksEmpty = tasks.isEmpty()
                    )
                }
                logger.d("Task Stream: $tasks")
            }
        }
    }

    override fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ToggleDropdownMenu -> updateState {
                it.copy(showSortDropdown = event.value)
            }

            is HomeScreenEvent.OnSortMethodChanged -> updateState { state ->
                val sorted = state.tasks?.let { sort(it, event.sortingMethod) }
                state.copy(
                    tasks = sorted,
                    sortingMethod = event.sortingMethod,
                    showSortDropdown = false
                )
            }

            is HomeScreenEvent.OnTabSelected -> updateState {
                it.copy(selectedTab = event.tab)
            }

            is HomeScreenEvent.OnTasksReordered -> updateState {
                it.copy(tasks = event.tasks)
            }

            is HomeScreenEvent.CompleteTask -> {
                viewModelScope.launch {
                    taskRepository.update(event.task.copy(isCompleted = true))
                }
            }

            is HomeScreenEvent.UndoCompletedTask -> {
                viewModelScope.launch {
                    taskRepository.update(event.task.copy(isCompleted = false))
                }
            }

            is HomeScreenEvent.RemoveTask -> {
                viewModelScope.launch {
                    taskRepository.delete(event.task)
                }
            }

            is HomeScreenEvent.UndoRemovedTask -> {
                viewModelScope.launch {
                    taskRepository.add(event.task)
                }
            }

            HomeScreenEvent.InsertTestData -> updateState {
                // TODO insert from file
                it.copy(showMenu = false)
            }

            HomeScreenEvent.ClearTasks -> updateState {
                viewModelScope.launch {
                    taskRepository.clear()
                }
                it.copy(showMenu = false)
            }

            is HomeScreenEvent.ToggleClearDialog -> updateState {
                it.copy(
                    clearDialogState = event.value,
                    showMenu = false
                )
            }

            is HomeScreenEvent.ToggleMenu -> updateState {
                it.copy(showMenu = event.value)
            }

            is HomeScreenEvent.NavigateToSettings -> updateState {
                it.copy(
                    showMenu = false,
                    navigateToSettings = event.value
                )
            }

            is HomeScreenEvent.ToggleSpotlight -> updateState {
                it.copy(isSpotlightShowed = event.showed)
            }
        }
    }

    private fun sort(tasks: List<Task>, sortingMethod: SortingMethod): List<Task> {
        return when (sortingMethod) {
            SortingMethod.Default -> tasks.sortedByDescending { it.createdAt }
            SortingMethod.Priority -> tasks.sortedByDescending { it.priority }
            SortingMethod.Date -> tasks.sortedByDescending { it.date }
            SortingMethod.Alphabetically -> tasks.sortedBy { it.title }
        }
    }
}
