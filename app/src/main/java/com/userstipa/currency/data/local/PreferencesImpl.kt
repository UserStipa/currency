package com.userstipa.currency.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PreferencesImpl @Inject constructor(
    private val context: Context
) : Preferences {

    private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    override suspend fun setPreferences(key: PreferencesKeys, values: Set<String>) {
        val stringSetKey = stringSetPreferencesKey(key.name)
        context.dataStore.edit { preferences ->
            preferences[stringSetKey] = values
        }
    }

    override suspend fun getPreferences(key: PreferencesKeys): Set<String> {
        val stringSetKey = stringSetPreferencesKey(key.name)
        val preferences = context.dataStore.data.first()
        return preferences[stringSetKey] ?: emptySet()
    }

    companion object {
        private const val PREFERENCES_NAME = "PREFERENCES_NAME"
    }
}