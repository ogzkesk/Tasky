package com.ogzkesk.tasky.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ogzkesk.domain.prefs.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        db: Class<RoomDatabase>,
    ): RoomDatabase {
        return Room.databaseBuilder(context, db, "base.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEncryptedDatabase(
        @ApplicationContext context: Context,
        preferencesRepository: PreferencesRepository,
        db: Class<RoomDatabase>,
    ): RoomDatabase {
        val supportFactory = SupportOpenHelperFactory(preferencesRepository.getCipherKey())
        return Room.databaseBuilder(context, db, "encrypted.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(supportFactory)
            .build()
    }
}
