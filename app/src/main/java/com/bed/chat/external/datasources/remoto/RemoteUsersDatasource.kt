package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import io.ktor.client.HttpClient
import io.ktor.client.request.url

import com.bed.chat.data.datasources.UsersDatasource

import com.bed.chat.external.clients.http.request
import com.bed.chat.external.clients.http.HttpUrl
import com.bed.chat.external.clients.http.response.UserResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

class RemoteUsersDatasource @Inject constructor(private val client: HttpClient) : UsersDatasource {
    override suspend fun getUser(parameter: Int): Result<UserResponse> =
        client.request<UserResponse> {
            method = HttpMethod.Get
            url("${HttpUrl.USERS.value}/$parameter")
        }

    override suspend fun getUsers(parameter: PaginationRequest): Result<PaginatedResponse<UserResponse>> =
        client.request<PaginatedResponse<UserResponse>> {
            method = HttpMethod.Get
            url(configurePaginationParameter(HttpUrl.USERS, parameter))
        }
}
