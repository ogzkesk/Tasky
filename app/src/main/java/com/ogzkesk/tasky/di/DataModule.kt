package com.ogzkesk.tasky.di

import android.content.Context
import com.ogzkesk.database.logger.LoggerImpl
import com.ogzkesk.database.permission.PermissionManagerImpl
import com.ogzkesk.database.task.TaskCreationCache
import com.ogzkesk.database.task.TaskRepositoryImpl
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.permission.PermissionManager
import com.ogzkesk.domain.task.TaskDataSource
import com.ogzkesk.domain.task.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideLogger(): Logger = LoggerImpl()

    @Provides
    @Singleton
    fun providePermissionManager(
        @ApplicationContext context: Context,
    ): PermissionManager = PermissionManagerImpl(context)

    @Provides
    @Singleton
    fun provideTaskRepository(
        localDataSource: TaskDataSource,
    ): TaskRepository = TaskRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideTaskCreationCache(
        logger: Logger,
    ): TaskCreationCache = TaskCreationCache(logger)
}
