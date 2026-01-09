package com.codexdroid.officetoffice.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codexdroid.officetoffice.utils.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {
    private val checkInTimeKey = longPreferencesKey("check_in_time")
    private val checkOutTimeKey = longPreferencesKey("check_out_time")
    private val isFirstTimeKey = booleanPreferencesKey("is_first_time")

    suspend fun saveCheckInTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[checkInTimeKey] = time
        }
    }

    suspend fun saveCheckOutTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[checkOutTimeKey] = time
        }
    }

    suspend fun doOnboardingDone() {
        context.dataStore.edit { preferences ->
            preferences[isFirstTimeKey] = true
        }
    }

    fun getCheckInTime(): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[checkInTimeKey] ?: 0L
        }.distinctUntilChanged()
    }

    fun getCheckOutTime(): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[checkOutTimeKey] ?: 0L
        }.distinctUntilChanged()
    }

    fun getIsOnboardingDone(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[isFirstTimeKey] ?: false
        }.distinctUntilChanged()
    }

}