package com.bed.chat.data.datasources

import com.bed.chat.data.alias.RemoteSignUpType
import com.bed.chat.data.alias.RemoteSignInType

import com.bed.chat.external.clients.request.SignInRequest
import com.bed.chat.external.clients.request.SignUpRequest

interface AuthenticationDatasource {
    suspend fun signUp(parameter: SignInRequest): RemoteSignUpType
    suspend fun signIn(parameter: SignUpRequest): RemoteSignInType
}
