package com.bed.chat.domain.repositories.storage

import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.Constants

interface StorageRepository {
    suspend fun delete(data: String)
    suspend fun get(data: String): Flow<String>
    suspend fun save(data: Pair<Constants, String>)
}
