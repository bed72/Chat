package com.bed.chat.data.datasources.storage

import kotlinx.coroutines.flow.Flow

interface StorageDatasource {
    suspend fun delete(value: String)

    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}
