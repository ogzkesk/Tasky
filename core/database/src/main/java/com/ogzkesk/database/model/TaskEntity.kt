package com.ogzkesk.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ogzkesk.domain.model.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    val title: String,
    val description: String?,
    val priority: Task.Priority,
    val isCompleted: Boolean,
    val createdAt: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
