package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.external.modules.IoDispatcher
import com.bed.chat.external.clients.request.toRequest

import com.bed.chat.data.datasources.AuthenticationDatasource

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.models.output.ImageOutputModel
import com.bed.chat.domain.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: AuthenticationDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {
    override suspend fun validateToken(token: String): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                val response = datasource.validateToken(token)

                // TODO: persistir dados do usuário.
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
               val response =datasource.signIn(parameter.toRequest())
           }
        }

    override suspend fun uploadProfilePicture(parameter: String): Result<ImageOutputModel> =
        withContext(ioDispatcher) {
            runCatching {
                datasource.uploadProfilePicture(parameter).map {
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
