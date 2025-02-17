package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import com.bed.chat.data.datasources.UserDatasource

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.user.PaginatedUserResponse

class RemoteUserDatasource @Inject constructor(private val client: HttpClient) : UserDatasource {
    override suspend fun invoke(parameter: PaginationRequest): Result<PaginatedUserResponse> =
        client.http.request<PaginatedUserResponse> {
            method = HttpMethod.Get
            url { configurePaginationParameter(HttpUrl.USERS, parameter) }
        }
}
