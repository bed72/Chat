package com.bed.chat.domain.repositories

import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.Constants

interface DataStoreRepository {
    suspend fun delete(data: String)
    suspend fun get(data: String): Flow<String>
    suspend fun save(data: Pair<Constants, String>)
}
