package com.bed.chat.external.datasources

import com.bed.chat.data.alias.RemoteSignInType
import com.bed.chat.data.alias.RemoteSignUpType
import javax.inject.Inject

import io.ktor.http.HttpMethod

import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient

import com.bed.chat.external.clients.request.SignInRequest
import com.bed.chat.external.clients.request.SignUpRequest
import com.bed.chat.external.clients.response.TokenResponse

import com.bed.chat.data.datasources.AuthenticationDatasource

class RemoteAuthenticationDatasource @Inject constructor(
    private val client: HttpClient
) : AuthenticationDatasource {
    override suspend fun signUp(parameter: SignInRequest): RemoteSignUpType =
        client.http.request<String, Unit> {
            setBody(parameter)
            method = HttpMethod.Post
            url(HttpUrl.SIGN_UP.value)
        }

    override suspend fun signIn(parameter: SignUpRequest): RemoteSignInType =
        client.http.request<String, TokenResponse> {
            setBody(parameter)
            method = HttpMethod.Post
            url(HttpUrl.SIGN_IN.value)
        }
}
