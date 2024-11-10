package com.bed.chat.data.datasources

import com.bed.chat.data.alias.DataSignUpType
import com.bed.chat.data.alias.DataSignInType

import com.bed.chat.external.clients.request.SignInRequest
import com.bed.chat.external.clients.request.SignUpRequest

interface AuthenticationDatasource {
    suspend fun signUp(parameter: SignUpRequest): DataSignUpType
    suspend fun signIn(parameter: SignInRequest): DataSignInType
}
