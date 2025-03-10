package com.ogzkesk.domain.task

import com.ogzkesk.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {
    fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: Long): Flow<Task?>
    suspend fun insertTask(task: Task) : Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun clear()
}