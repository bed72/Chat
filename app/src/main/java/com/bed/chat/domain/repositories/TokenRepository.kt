package com.bed.chat.domain.repositories

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun delete()
    suspend fun get(): Flow<String>
    suspend fun save(parameter: String)
}
