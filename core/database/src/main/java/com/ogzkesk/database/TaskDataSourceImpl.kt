package com.ogzkesk.database

import android.content.Context
import com.ogzkesk.database.model.TaskEntity
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.model.Task
import com.ogzkesk.domain.task.TaskDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class TaskDataSourceImpl(
    private val dao: TaskDao,
    private val logger: Logger,
    @ApplicationContext private val context: Context
) : TaskDataSource {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map { list ->
            list.map(TaskEntity::toModel)
        }
    }

    override suspend fun getTaskById(id: Long): Flow<Task?> {
        return dao.getTaskById(id).map { it?.toModel() }
    }

    override suspend fun insertTask(task: Task): Long {
        return dao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.delete(task.toEntity())
    }

    override suspend fun clear() {
        dao.deleteAllTasks()
    }

    override suspend fun addSample() {
        val json = context.assets.open("sample.json")
            .bufferedReader()
            .use { it.readText() }

        val tasks = Json.decodeFromString<List<Task>>(json)
        logger.d("Sample data -> $tasks")
        dao.insertTasks(tasks.map(Task::toEntity))
    }
}