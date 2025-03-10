package com.ogzkesk.tasky.ui.creation

import androidx.lifecycle.viewModelScope
import com.ogzkesk.database.mvi.ViewModel
import com.ogzkesk.database.task.TaskCreationCache
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCache: TaskCreationCache,
    private val logger: Logger
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
            is CreationScreenEvent.DescriptionTextChangedEvent -> {
                taskCache.update { it.copy(description = event.text) }
            }

            is CreationScreenEvent.PriorityChangedEvent -> {
                taskCache.update { it.copy(priority = event.priority) }
            }

            is CreationScreenEvent.TitleTextChangedEvent -> {
                if (event.text.isNotBlank()) {
                    updateState { it.copy(titleFieldError = false) }
                }
                taskCache.update { it.copy(title = event.text) }
            }

            is CreationScreenEvent.OnDateSelected -> {
                updateState {
                    it.copy(dateFieldError = false)
                }
                taskCache.update { it.copy(date = event.millis) }
            }

            is CreationScreenEvent.OnCreate -> withState {
                viewModelScope.launch {
                    if (validateFields()) {
                        val result = taskRepository.add(
                            task.copy(createdAt = System.currentTimeMillis())
                        )
                        event.callback()
                        taskCache.clear()
                        logger.d("added task: $result")
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        if (state.value.task.title.isBlank()) {
            updateState {
                it.copy(titleFieldError = true)
            }
            return false
        }
        if (state.value.task.date == 0L) {
            updateState {
                it.copy(dateFieldError = true)
            }
            return false
        }
        return true
    }
}
