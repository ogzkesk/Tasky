package com.ogzkesk.tasky.ui.detail

import com.ogzkesk.database.mvi.ViewEvent

sealed interface DetailScreenEvent : ViewEvent {
    data class ToggleTaskCompleted(val value: Boolean) : DetailScreenEvent
    data class ToggleTrashDialog(val value: Boolean) : DetailScreenEvent
    data object DeleteTask : DetailScreenEvent
}