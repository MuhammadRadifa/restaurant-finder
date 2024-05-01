package com.dicoding.core.data.source.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.core.data.common.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.SETTINGS_PREFERENCE)

class SettingPreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun saveUsername(token: String) {
        dataStore.edit { preferences ->
            preferences[usernamekey] = token
        }
    }

    fun getUsername(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[usernamekey]
        }
    }

    companion object {
        private const val USERNAME_KEY = "username"
        private val usernamekey = stringPreferencesKey(USERNAME_KEY)
    }
}