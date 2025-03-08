package com.ogzkesk.template.di

import android.content.Context
import com.ogzkesk.data.logger.LoggerImpl
import com.ogzkesk.data.permission.PermissionManagerImpl
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.permission.PermissionManager
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
}
