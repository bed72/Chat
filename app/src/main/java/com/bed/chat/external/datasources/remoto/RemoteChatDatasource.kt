package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import io.ktor.client.HttpClient
import io.ktor.client.request.url

import com.bed.chat.data.datasources.ChatDatasource

import com.bed.chat.external.clients.http.request
import com.bed.chat.external.clients.http.HttpUrl
import com.bed.chat.external.clients.http.response.ChatResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

class RemoteChatDatasource @Inject constructor(private val client: HttpClient) : ChatDatasource {
    override suspend fun invoke(parameter: PaginationRequest): Result<PaginatedResponse<ChatResponse>> =
        client.request<PaginatedResponse<ChatResponse>> {
            method = HttpMethod.Get
            url(configurePaginationParameter(HttpUrl.CONVERSATIONS, parameter))
        }
}
