package com.bed.chat.data.datasources

import com.bed.chat.data.alias.DataSignUpType
import com.bed.chat.data.alias.DataSignInType

import com.bed.chat.external.clients.request.SignInRequest
import com.bed.chat.external.clients.request.SignUpRequest

interface AuthenticationDatasource {
    suspend fun signUp(parameter: SignInRequest): DataSignUpType
    suspend fun signIn(parameter: SignUpRequest): DataSignInType
}
