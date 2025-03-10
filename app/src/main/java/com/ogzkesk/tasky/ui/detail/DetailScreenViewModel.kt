package com.ogzkesk.tasky.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ogzkesk.database.mvi.ViewModel
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.task.TaskRepository
import com.ogzkesk.domain.util.IoDispatcher
import com.ogzkesk.tasky.navigation.DetailScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val logger: Logger,
) : ViewModel<DetailScreenState, DetailScreenEvent>(DetailScreenState()) {

    init {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.getById(
                id = savedStateHandle.toRoute<DetailScreenRoute>().id
            ).collect { task ->
                updateState { state ->
                    task?.let { state.copy(task = task) } ?: state.copy(isDeleted = true)
                }
            }
        }
    }

    override fun onEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.ToggleTaskCompleted -> withState {
                viewModelScope.launch {
                    task?.let {
                        taskRepository.update(
                            it.copy(isCompleted = event.value)
                        )
                    }
                }
            }

            is DetailScreenEvent.ToggleTrashDialog -> withState {
                updateState {
                    it.copy(showTrashDialog = event.value)
                }
            }

            is DetailScreenEvent.DeleteTask -> withState {
                viewModelScope.launch {
                    task?.let {
                        taskRepository.delete(it)
                        logger.d("deleted task: $it")
                    }
                }
            }
        }
    }
}
