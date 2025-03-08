package com.ogzkesk.domain.prefs

import com.ogzkesk.domain.prefs.model.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getPreferences(): Flow<Preferences>

    suspend fun setPreferences(block: (Preferences) -> Preferences)

    fun <T> getEncryptedPreferences(
        key: String,
        default: T,
    ): T

    fun <T> setEncryptedPreferences(
        key: String,
        value: T,
    )

    suspend fun clearPreferences()

    fun clearEncryptedPreferences()

    fun getCipherKey(): ByteArray
}
