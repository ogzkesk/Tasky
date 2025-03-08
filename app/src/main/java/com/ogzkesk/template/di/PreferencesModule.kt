package com.ogzkesk.template.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.ogzkesk.data.prefs.PreferenceSerializer
import com.ogzkesk.data.prefs.PreferencesRepositoryImpl
import com.ogzkesk.domain.prefs.PreferencesRepository
import com.ogzkesk.domain.prefs.model.Preferences
import com.ogzkesk.domain.util.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "enc_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): DataStore<Preferences> {
        return DataStoreFactory.create(
            serializer = PreferenceSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(
        preferences: DataStore<Preferences>,
        encryptedSharedPreferences: SharedPreferences,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): PreferencesRepository =
        PreferencesRepositoryImpl(
            preferences = preferences,
            encryptedSharedPreferences = encryptedSharedPreferences,
            ioDispatcher = ioDispatcher,
        )
}
