package com.ogzkesk.tasky.ui.home

import com.ogzkesk.domain.model.Task

data class HomeScreenState(
    val tasks: List<Task>? = null,
)
