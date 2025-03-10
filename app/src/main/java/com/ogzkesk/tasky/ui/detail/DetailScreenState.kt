package com.ogzkesk.tasky.ui.detail

import com.ogzkesk.domain.model.Task

data class DetailScreenState(
    val task: Task? = null,
    val showTrashDialog: Boolean = false,
    val isDeleted: Boolean = false
)
