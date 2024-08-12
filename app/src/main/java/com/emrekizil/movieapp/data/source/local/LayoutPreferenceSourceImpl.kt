package com.emrekizil.movieapp.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LayoutPreferenceSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LayoutPreferenceSource{

    override suspend fun saveLayoutPreference(isGridMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_LAYOUT] = isGridMode
        }
    }

    override fun getLayoutPreference(): Flow<Boolean> =
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.SELECTED_LAYOUT] ?: false
        }


    private object PreferencesKeys {
        val SELECTED_LAYOUT = booleanPreferencesKey("layout")
    }
}