package com.ogzkesk.database

import com.ogzkesk.database.model.TaskEntity
import com.ogzkesk.domain.model.Task
import com.ogzkesk.domain.task.TaskDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskDataSourceImpl(
    private val dao: TaskDao
) : TaskDataSource {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map { list ->
            list.map(TaskEntity::toModel)
        }
    }

    override suspend fun getTaskById(id: Long): Flow<Task?> {
        return dao.getTaskById(id).map { it?.toModel() }
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toEntity())
    }

    override suspend fun clear() {
        dao.deleteAllTasks()
    }
}