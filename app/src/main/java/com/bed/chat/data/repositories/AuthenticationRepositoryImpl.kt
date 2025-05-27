package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.data.datasources.NotificationDatasource
import com.bed.chat.data.datasources.AuthenticationDatasource

import com.bed.chat.external.modules.IoDispatcher

import com.bed.chat.external.clients.http.response.toModel
import com.bed.chat.external.clients.http.request.toRequest
import com.bed.chat.external.clients.http.request.TokenRequest
import com.bed.chat.external.clients.http.response.UserResponse

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.models.output.UserOutputModel
import com.bed.chat.domain.models.output.ImageOutputModel

import com.bed.chat.domain.repositories.TokenRepository
import com.bed.chat.domain.repositories.FirebaseTokenRepository
import com.bed.chat.domain.repositories.AuthenticationRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository

class AuthenticationRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val selfUserRepository: SelfUserRepository,
    private val notificationDatasource: NotificationDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val firebaseTokenRepository: FirebaseTokenRepository,
    private val authenticationDatasource: AuthenticationDatasource,
) : AuthenticationRepository {

    override val currentUser: Flow<UserOutputModel> get() =
        selfUserRepository
            .user
            .flowOn(ioDispatcher)

    override suspend fun authenticate(): Result<Unit> =
        safeCallResult(ioDispatcher) {
            authenticationDatasource.authenticate().onSuccess {
                save(it)
                saveNotificationToken()
            }
            Unit
        }

    override suspend fun signUp(parameter: SignUpInputModel): Result<Unit> =
        safeCallResult(ioDispatcher) {
            authenticationDatasource.signUp(parameter.toRequest()).getOrThrow()
        }

    override suspend fun signIn(parameter: SignInInputModel): Result<Unit> =
        safeCallResult(ioDispatcher) {
            val response = authenticationDatasource.signIn(parameter.toRequest()).getOrThrow()

            if (response.token.isNotEmpty()) tokenRepository.save(response.token)

            authenticate()
        }

    override suspend fun uploadProfilePicture(parameter: String): Result<ImageOutputModel> =
        safeCallResult(ioDispatcher) {
            authenticationDatasource.uploadProfilePicture(parameter).map { it.toModel() }.getOrThrow()
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

    private suspend fun saveNotificationToken() {
        val token = firebaseTokenRepository.getToken()
        notificationDatasource.registerToken(TokenRequest(token))
    }
}
