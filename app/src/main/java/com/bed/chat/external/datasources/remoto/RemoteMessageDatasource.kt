package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import com.bed.chat.data.datasources.MessageDatasource

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient

import com.bed.chat.external.clients.response.MessageResponse
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.PaginatedResponse

class RemoteMessageDatasource @Inject constructor(private val client: HttpClient) : MessageDatasource {
    override suspend fun invoke(parameter: Pair<Int, PaginationRequest>): Result<PaginatedResponse<MessageResponse>> =
        client.http.request<PaginatedResponse<MessageResponse>> {
            method = HttpMethod.Get
            url { configurePaginationParameter(HttpUrl.MESSAGES, parameter.second, parameter.first) }
        }
}
