package com.bed.chat.domain.repositories.storage

import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.Constants

interface StorageRepository {
    suspend fun delete(parameter: Constants)
    suspend fun get(parameter: Constants): Flow<String>
    suspend fun save(parameter: Pair<Constants, String>)
}
