package com.bed.chat.external.datasources

import javax.inject.Inject

import io.ktor.http.HttpMethod

import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.chat.data.alias.DataSignInType
import com.bed.chat.data.alias.DataSignUpType

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient

import com.bed.chat.external.clients.request.SignInRequest
import com.bed.chat.external.clients.response.TokenResponse

import com.bed.chat.data.datasources.AuthenticationDatasource
import com.bed.chat.external.clients.request.SignUpRequest
import com.bed.chat.external.clients.response.FailureResponse

class RemoteAuthenticationDatasource @Inject constructor(
    private val client: HttpClient
) : AuthenticationDatasource {
    override suspend fun signUp(parameter: SignUpRequest): DataSignUpType =
        client.http.request<FailureResponse, Unit> {
            setBody(parameter)
            method = HttpMethod.Post
            url(HttpUrl.SIGN_UP.value)
        }

    override suspend fun signIn(parameter: SignInRequest): DataSignInType =
        client.http.request<FailureResponse, TokenResponse> {
            setBody(parameter)
            method = HttpMethod.Post
            url(HttpUrl.SIGN_IN.value)
        }
}
