package com.bed.chat.data.repositories

import android.util.Log

import javax.inject.Inject

import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.external.modules.IoDispatcher
import com.bed.chat.external.clients.request.toRequest

import com.bed.chat.data.datasources.AuthenticationDatasource

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: AuthenticationDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {
    override suspend fun signUp(parameter: SignUpInputModel): Result<Unit> =
        withContext(ioDispatcher) { datasource.signUp(parameter.toRequest()) }

    override suspend fun signIn(parameter: SignInInputModel): Result<Unit> =
        withContext(ioDispatcher) {
            datasource.signIn(parameter.toRequest())
                .onSuccess { Log.d("[Success]", it.token) }
                .onFailure { Log.d("[Failure]", it.message.toString()) }

            Result.success(Unit)
    }
}
