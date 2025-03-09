package com.ogzkesk.tasky.ui.creation

import androidx.lifecycle.viewModelScope
import com.ogzkesk.database.mvi.ViewModel
import com.ogzkesk.database.task.TaskCreationCache
import com.ogzkesk.domain.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCache: TaskCreationCache,
) : ViewModel<CreationScreenState, CreationScreenEvent>(CreationScreenState()) {

    init {
        viewModelScope.launch {
            taskCache.stream().collect { task ->
                updateState {
                    it.copy(task = task)
                }
            }
        }
    }

    override fun onEvent(event: CreationScreenEvent) {
        when (event) {
            is CreationScreenEvent.OnCreate -> withState {
                viewModelScope.launch {
                    // TODO fieldValidation.

                    val result = taskRepository.add(
                        task.copy(createdAt = System.currentTimeMillis())
                    )

                    // TODO process result

                    event.callback()
                    taskCache.clear()
                }
            }

            is CreationScreenEvent.DescriptionTextChangedEvent -> {
                taskCache.update { it.copy(description = event.text) }
            }

            is CreationScreenEvent.PriorityChangedEvent -> {
                taskCache.update { it.copy(priority = event.priority) }
            }

            is CreationScreenEvent.TitleTextChangedEvent -> {
                taskCache.update { it.copy(title = event.text) }
            }
        }
    }
}
