package com.bed.chat.data.repositories

import javax.inject.Inject

import com.bed.chat.external.clients.request.toRequest

import com.bed.chat.data.datasources.AuthenticationDatasource

import com.bed.chat.domain.alias.DomainSignInType
import com.bed.chat.domain.alias.DomainSignUpType
import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: AuthenticationDatasource
) : AuthenticationRepository {
    override suspend fun signUp(parameter: SignUpInputModel): DomainSignUpType =
        datasource.signUp(parameter.toRequest())

    override suspend fun signIn(parameter: SignInInputModel): DomainSignInType =
        datasource.signIn(parameter.toRequest())
            .map{
                // TODO save token
            }
}
