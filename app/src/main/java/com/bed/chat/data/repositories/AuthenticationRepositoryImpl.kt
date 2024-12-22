package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.domain.Constants
import com.bed.chat.external.modules.IoDispatcher
import com.bed.chat.external.clients.request.toRequest

import com.bed.chat.data.datasources.AuthenticationDatasource

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.models.output.ImageOutputModel

import com.bed.chat.domain.repositories.AuthenticationRepository
import com.bed.chat.domain.repositories.storage.StorageRepository
import com.bed.chat.domain.repositories.storage.SelfUserStorageRepository

class AuthenticationRepositoryImpl @Inject constructor(
    private val storageRepository: StorageRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userStorageRepository: SelfUserStorageRepository,
    private val authenticationDatasource: AuthenticationDatasource
) : AuthenticationRepository {
    override suspend fun validateToken(token: String): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
               authenticationDatasource.validateToken(token).onSuccess {
                   userStorageRepository.save(
                       username = it.username,
                       lastName = it.lastName,
                       firstName = it.firstName,
                       pictureUrl = it.profilePicture ?: ""
                   )
               }

                Unit
            }
        }

    override suspend fun signUp(parameter: SignUpInputModel): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                authenticationDatasource.signUp(parameter.toRequest()).getOrThrow()
            }
        }

    override suspend fun signIn(parameter: SignInInputModel): Result<Unit> =
        withContext(ioDispatcher) {
           runCatching {
               val response = authenticationDatasource.signIn(parameter.toRequest()).getOrDefault(null)

               if (response != null) storageRepository.save(Constants.USER_TOKEN to response.token)
           }
        }

    override suspend fun uploadProfilePicture(parameter: String): Result<ImageOutputModel> =
        withContext(ioDispatcher) {
            runCatching {
                authenticationDatasource.uploadProfilePicture(parameter).map {
                    ImageOutputModel(
                        id = it.id,
                        url = it.url,
                        name = it.name,
                        type = it.type,
                    )
                }.getOrThrow()
            }
        }
}
