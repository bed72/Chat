package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.external.modules.IoDispatcher

import com.bed.chat.data.datasources.TokenDatasource
import com.bed.chat.domain.Constants

import com.bed.chat.domain.repositories.TokenRepository
import com.bed.chat.domain.repositories.storage.StorageRepository
import com.bed.chat.domain.repositories.storage.SelfUserStorageRepository

class TokenRepositoryImpl @Inject constructor(
    private val datasource: TokenDatasource,
    private val storageRepository: StorageRepository,
    private val selfUserRepository: SelfUserStorageRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TokenRepository {

    override suspend fun delete() = storageRepository.delete(Constants.USER_TOKEN)

    override suspend fun get(): Flow<String> = storageRepository.get(Constants.USER_TOKEN)

    override suspend fun save(parameter: String) = storageRepository.save(Constants.USER_TOKEN to parameter)

    override suspend fun validate(token: String): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                datasource.validateToken(token).onSuccess {
                    selfUserRepository.save(
                        id = it.id,
                        username = it.username,
                        lastName = it.lastName,
                        firstName = it.firstName,
                        pictureUrl = it.profilePicture ?: ""
                    )
                }

                Unit
            }
        }
}
