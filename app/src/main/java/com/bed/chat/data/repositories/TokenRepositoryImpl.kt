package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.Constants

import com.bed.chat.domain.repositories.TokenRepository
import com.bed.chat.domain.repositories.storage.StorageRepository

class TokenRepositoryImpl @Inject constructor(
    private val repository: StorageRepository
) : TokenRepository {

    override suspend fun delete() = repository.delete(Constants.USER_TOKEN)

    override suspend fun get(): Flow<String> = repository.get(Constants.USER_TOKEN)

    override suspend fun save(parameter: String) = repository.save(Constants.USER_TOKEN to parameter)
}
