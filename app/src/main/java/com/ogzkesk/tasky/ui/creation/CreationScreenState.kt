package com.ogzkesk.tasky.ui.creation

import com.ogzkesk.domain.model.Task

data class CreationScreenState(
    val task: Task = Task.EMPTY,
)
