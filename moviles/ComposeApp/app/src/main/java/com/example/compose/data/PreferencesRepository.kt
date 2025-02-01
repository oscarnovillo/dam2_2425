package com.example.compose.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject




// Claves para las preferencias
object PreferencesKeys {
    val USER_NAME = stringPreferencesKey("user_name")
}


class PreferencesRepository @Inject constructor (private val dataStore: DataStore<Preferences>) {

    val userName: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_NAME].orEmpty()
        }

    // Guardar el nombre de usuario
    suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
        }
    }
    suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_NAME)
        }
    }
}