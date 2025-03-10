package com.ogzkesk.tasky.ui.creation

import com.ogzkesk.database.mvi.ViewEvent
import com.ogzkesk.domain.model.Task

sealed interface CreationScreenEvent : ViewEvent {
    data class OnCreate(val callback: () -> Unit) : CreationScreenEvent
    data class TitleTextChangedEvent(val text: String) : CreationScreenEvent
    data class DescriptionTextChangedEvent(val text: String) : CreationScreenEvent
    data class PriorityChangedEvent(val priority: Task.Priority) : CreationScreenEvent
    data class OnDateSelected(val millis: Long) : CreationScreenEvent
}