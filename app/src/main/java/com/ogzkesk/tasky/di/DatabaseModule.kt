package com.ogzkesk.tasky.di

import android.content.Context
import androidx.room.Room
import com.ogzkesk.database.TaskDao
import com.ogzkesk.database.TaskDataSourceImpl
import com.ogzkesk.database.TaskDatabase
import com.ogzkesk.domain.task.TaskDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "task.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase) = db.taskDao

    @Provides
    @Singleton
    fun provideTaskDataSource(
        dao: TaskDao
    ): TaskDataSource = TaskDataSourceImpl(dao)
}
