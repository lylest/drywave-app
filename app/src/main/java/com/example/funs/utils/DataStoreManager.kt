package com.example.funs.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user-token")
class DataStoreManager(context: Context) {

    private val dataStore = context.datastore

    companion object {
        val userTokenKey = stringPreferencesKey("user-token-key")
        val userIdkey = stringPreferencesKey("user-id-key")
    }

    suspend fun setUserToken(newToken: String) {
        dataStore.edit { pref ->
            pref[userTokenKey] = newToken

        }
    }

    suspend fun setUserId(newId: String) {
        dataStore.edit { pref ->
            pref[userIdkey] = newId
        }
    }

    fun getUserId(): Flow<String> {
        return dataStore.data
            .catch {  exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw  exception
                }
            }
            .map { pref ->
                pref[userIdkey] ?: "not-found"
            }
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { pref ->
                pref[userTokenKey] ?: "not-found"
            }
    }
}


