package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import com.bed.chat.data.datasources.UserDatasource

import com.bed.chat.external.clients.http.request
import com.bed.chat.external.clients.http.HttpUrl
import com.bed.chat.external.clients.http.HttpClient
import com.bed.chat.external.clients.http.response.UserResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

class RemoteUserDatasource @Inject constructor(private val client: HttpClient) : UserDatasource {
    override suspend fun invoke(parameter: PaginationRequest): Result<PaginatedResponse<UserResponse>> =
        client.http.request<PaginatedResponse<UserResponse>> {
            method = HttpMethod.Get
            url { configurePaginationParameter(HttpUrl.USERS, parameter) }
        }
}
