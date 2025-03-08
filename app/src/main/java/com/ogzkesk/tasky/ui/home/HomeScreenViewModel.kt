package com.ogzkesk.tasky.ui.home

import androidx.lifecycle.viewModelScope
import com.ogzkesk.database.mvi.ViewModel
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.task.TaskRepository
import com.ogzkesk.domain.util.IoDispatcher
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
                updateState {
                    it.copy(tasks = tasks)
                }
                logger.d("Task Stream: $tasks")
            }
        }
    }

    override fun onEvent(event: HomeScreenEvent) {
        when(event){

            else -> {}
        }
    }
}
