package com.bed.chat.data.datasources

import kotlinx.coroutines.flow.Flow

interface DataStoreDatasource {
    suspend fun delete(value: String)

    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}
