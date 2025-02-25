package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import com.bed.chat.data.datasources.ChatDatasource

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient
import com.bed.chat.external.clients.response.ChatResponse
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.PaginatedResponse

class RemoteChatDatasource @Inject constructor(private val client: HttpClient) : ChatDatasource {
    override suspend fun invoke(parameter: PaginationRequest): Result<PaginatedResponse<ChatResponse>> =
        client.http.request<PaginatedResponse<ChatResponse>> {
            method = HttpMethod.Get
            url { configurePaginationParameter(HttpUrl.CONVERSATIONS, parameter) }
        }
}
