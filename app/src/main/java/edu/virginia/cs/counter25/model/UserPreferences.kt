package edu.virginia.cs.counter25.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class UserPreferences(
    context: Context
) {
    private val dataStore: DataStore<Preferences> = context.applicationContext.preferencesDataStore

    companion object {
        const val PREFERENCE_ACCORDION_KEY = "accordion_expanded"

        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferences(context).also { INSTANCE = it }
            }
        }

        // Private property to ensure only the class uses the DataStore
        private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
    }

    // Save Boolean Preference
    suspend fun saveBooleanPreference(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    // Get Boolean Preference as Flow
    fun getBooleanPreference(key: String): Flow<Boolean> {
        val dataStoreKey = booleanPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[dataStoreKey] ?: false // Default value
        }
    }
}

