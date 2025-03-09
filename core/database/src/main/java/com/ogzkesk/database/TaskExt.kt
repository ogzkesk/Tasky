package com.ogzkesk.database

import com.ogzkesk.database.model.TaskEntity
import com.ogzkesk.domain.model.Task

fun TaskEntity.toModel(): Task = Task(
    title = title,
    description = description,
    priority = priority,
    isCompleted = isCompleted,
    createdAt = createdAt,
    id = id
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    title = title,
    description = description,
    priority = priority,
    isCompleted = isCompleted,
    createdAt = createdAt,
    id = id
)