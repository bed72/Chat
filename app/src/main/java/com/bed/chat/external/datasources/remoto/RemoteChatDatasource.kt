package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod
import io.ktor.http.HttpHeaders

import io.ktor.client.request.header

import com.bed.chat.data.datasources.ChatDatasource

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.PaginatedChatsResponse

class RemoteChatDatasource @Inject constructor(private val client: HttpClient) : ChatDatasource {
    override suspend fun getChats(
        token: String,
        parameter: PaginationRequest
    ): Result<PaginatedChatsResponse> =
        client.http.request<PaginatedChatsResponse> {
            method = HttpMethod.Get
            header(HttpHeaders.Authorization, "Bearer $parameter")
            url {
                host = HttpUrl.CHATS.value
                parameters.append("limit", parameter.limit.toString())
                parameters.append("offset", parameter.offset.toString())
            }
        }
}
