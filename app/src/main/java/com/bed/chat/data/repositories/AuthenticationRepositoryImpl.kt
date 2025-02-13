package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.external.modules.IoDispatcher

import com.bed.chat.external.clients.response.toModel
import com.bed.chat.external.clients.request.toRequest
import com.bed.chat.external.clients.response.UserResponse

import com.bed.chat.data.datasources.AuthenticationDatasource

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.models.output.ImageOutputModel

import com.bed.chat.domain.repositories.TokenRepository
import com.bed.chat.domain.repositories.AuthenticationRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository

class AuthenticationRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val datasource: AuthenticationDatasource,
    private val selfUserRepository: SelfUserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {

    override suspend fun authenticate(): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                datasource.authenticate().onSuccess { save(it) }
                Unit
            }
        }

    override suspend fun signUp(parameter: SignUpInputModel): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                datasource.signUp(parameter.toRequest()).getOrThrow()
            }
        }

    override suspend fun signIn(parameter: SignInInputModel): Result<Unit> =
        withContext(ioDispatcher) {
           runCatching {
               val response = datasource.signIn(parameter.toRequest()).getOrThrow()

               if (response.token.isNotEmpty()) tokenRepository.save(response.token)
           }
        }

    override suspend fun uploadProfilePicture(parameter: String): Result<ImageOutputModel> =
        withContext(ioDispatcher) {
            runCatching {
                datasource.uploadProfilePicture(parameter).map { it.toModel() }.getOrThrow()
            }
        }

    private suspend fun save(parameter: UserResponse) {
        selfUserRepository.save(
            id = parameter.id,
            username = parameter.username,
            lastName = parameter.lastName,
            firstName = parameter.firstName,
            pictureUrl = parameter.profilePicture ?: ""
        )
    }
}
