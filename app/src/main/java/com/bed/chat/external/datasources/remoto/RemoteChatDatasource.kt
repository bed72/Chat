package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import com.bed.chat.data.datasources.ChatDatasource

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.chat.PaginatedChatResponse

class RemoteChatDatasource @Inject constructor(private val client: HttpClient) : ChatDatasource {
    override suspend fun invoke(parameter: PaginationRequest): Result<PaginatedChatResponse> =
        client.http.request<PaginatedChatResponse> {
            method = HttpMethod.Get
            url { configurePaginationParameter(HttpUrl.CONVERSATIONS, parameter) }
        }
}
