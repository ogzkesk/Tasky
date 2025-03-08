package com.ogzkesk.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ogzkesk.database.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}