package com.ogzkesk.domain.task

import com.ogzkesk.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun stream(): Flow<List<Task>>
    suspend fun add(task: Task)
    suspend fun getById(id: Long): Task?
    suspend fun update(task: Task)
    suspend fun delete(task: Task)
    suspend fun clear()
}