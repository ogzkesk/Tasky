package com.ogzkesk.tasky.ui.home

import androidx.lifecycle.viewModelScope
import com.ogzkesk.database.mvi.ViewModel
import com.ogzkesk.domain.logger.Logger
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
                    state.copy(tasks = tasks)
                }
                logger.d("Task Stream: $tasks")
            }
        }
    }

    override fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ToggleDropdownMenu -> updateState {
                it.copy(showDropdownMenu = event.value)
            }

            is HomeScreenEvent.OnSortMethodChanged -> updateState { state ->
                val sorted = when (event.sortingMethod) {
                    SortingMethod.Default -> state.tasks?.sortedByDescending { it.createdAt }
                    SortingMethod.Priority -> state.tasks?.sortedByDescending { it.priority }
                    SortingMethod.Date -> state.tasks?.sortedByDescending { it.date }
                    SortingMethod.Alphabetically -> state.tasks?.sortedBy { it.title }
                }
                state.copy(
                    tasks = sorted,
                    sortingMethod = event.sortingMethod,
                    showDropdownMenu = false
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

            is HomeScreenEvent.RemoveTask -> {
                viewModelScope.launch {
                    taskRepository.delete(event.task)
                }
            }
        }
    }
}
