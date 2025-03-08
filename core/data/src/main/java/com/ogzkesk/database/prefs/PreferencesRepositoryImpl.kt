package com.ogzkesk.database.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import com.ogzkesk.domain.prefs.PreferencesRepository
import com.ogzkesk.domain.prefs.model.Preferences
import com.ogzkesk.domain.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.security.SecureRandom

class PreferencesRepositoryImpl(
    private val preferences: DataStore<Preferences>,
    private val encryptedSharedPreferences: SharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PreferencesRepository {
    override fun getPreferences(): Flow<Preferences> = preferences.data.flowOn(ioDispatcher)

    override suspend fun setPreferences(block: (Preferences) -> Preferences) {
        withContext(ioDispatcher) {
            preferences.updateData {
                block.invoke(getPreferences().first())
            }
        }
    }

    override suspend fun clearPreferences() {
        withContext(ioDispatcher) {
            preferences.updateData { Preferences() }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getEncryptedPreferences(
        key: String,
        default: T,
    ): T =
        encryptedSharedPreferences.run {
            when (default) {
                is Int -> getInt(key, default) as T
                is String -> getString(key, default) as T
                is Boolean -> getBoolean(key, default) as T
                is Float -> getFloat(key, default) as T
                is Long -> getLong(key, default) as T
                else -> error("Unsupported type")
            }
        }

    override fun <T> setEncryptedPreferences(
        key: String,
        value: T,
    ) {
        encryptedSharedPreferences.edit {
            when (value) {
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> error("Unsupported type")
            }
        }
    }

    override fun getCipherKey(): ByteArray {
        val key = getEncryptedPreferences("cipher_key", "")
        if (key.isNotEmpty()) {
            return key.toByteArray()
        }

        val newKey =
            ByteArray(32).apply {
                SecureRandom.getInstanceStrong().nextBytes(this)
            }.decodeToString()
        setEncryptedPreferences("cipher_key", newKey)
        return newKey.toByteArray()
    }

    override fun clearEncryptedPreferences() {
        encryptedSharedPreferences.edit {
            clear()
        }
    }
}
