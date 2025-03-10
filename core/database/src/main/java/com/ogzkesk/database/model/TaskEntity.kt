package com.ogzkesk.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ogzkesk.domain.model.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String?,
    @ColumnInfo("priority")
    val priority: Task.Priority,
    @ColumnInfo("is_completed")
    val isCompleted: Boolean,
    @ColumnInfo("created_at")
    val createdAt: Long,
    @ColumnInfo("date")
    val date: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
