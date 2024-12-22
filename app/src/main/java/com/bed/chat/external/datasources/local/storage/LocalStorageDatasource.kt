package com.bed.chat.external.datasources.local.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

import com.bed.chat.data.datasources.storage.StorageDatasource

class LocalStorageDatasource @Inject constructor(
    private val datastore: DataStore<Preferences>
) : StorageDatasource {
    override suspend fun delete(value: String) {
        datastore.edit { preferences -> preferences.remove(buildKey(value)) }
    }

    override suspend fun get(value: String): Flow<String> =
        datastore.data.map { preferences -> preferences[buildKey(value)].orEmpty() }

    override suspend fun save(value: Pair<String, String>) {
        datastore.edit { preferences -> preferences[buildKey(value.first)] = value.second }
    }

    private fun buildKey(value: String): Preferences.Key<String> = stringPreferencesKey(value)
}
