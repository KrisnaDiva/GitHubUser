package com.krisna.diva.githubuser.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getThemeSetting(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[THEME] ?: 0
        }
    }

    suspend fun saveThemeSetting(theme: Int) {
        dataStore.edit { preferences ->
            preferences[THEME] = theme
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        private val THEME = intPreferencesKey("theme_setting")

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}