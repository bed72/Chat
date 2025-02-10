package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpHeaders
import io.ktor.client.request.url
import io.ktor.client.request.header

import com.bed.chat.data.datasources.TokenDatasource

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient
import com.bed.chat.external.clients.response.UserResponse

class RemoteTokenDatasource @Inject constructor(
    private val client: HttpClient
) : TokenDatasource {
    override suspend fun validateToken(parameter: String): Result<UserResponse> =
        client.http.request<UserResponse> {
            url(HttpUrl.VALIDATE_TOKEN.value)
            header(HttpHeaders.Authorization, "Bearer $parameter")
        }
}
