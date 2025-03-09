package com.ogzkesk.database.task

import com.ogzkesk.domain.model.Task
import com.ogzkesk.domain.task.TaskDataSource
import com.ogzkesk.domain.task.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskDataSource
) : TaskRepository {

    override fun stream(): Flow<List<Task>> = localDataSource.getTasks()

    override suspend fun getById(id: Long): Flow<Task?> = localDataSource.getTaskById(id)

    override suspend fun add(task: Task) {
        localDataSource.insertTask(task)
    }

    override suspend fun update(task: Task) {
        localDataSource.updateTask(task)
    }

    override suspend fun delete(task: Task) {
        localDataSource.deleteTask(task)
    }

    override suspend fun moveToTrash(task: Task) {
        localDataSource.updateTask(
            task.copy(isDeleted = true)
        )
    }

    override suspend fun restoreFromTrash(task: Task) {
        localDataSource.updateTask(
            task.copy(isDeleted = false)
        )
    }

    override suspend fun complete(task: Task) {
        localDataSource.updateTask(
            task.copy(isCompleted = true)
        )
    }

    override suspend fun clear() {
        localDataSource.clear()
    }
}