package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import io.ktor.client.HttpClient
import io.ktor.client.request.url

import kotlinx.coroutines.flow.Flow

import com.bed.chat.data.datasources.RemoteMessageDatasource

import com.bed.chat.external.clients.http.request
import com.bed.chat.external.clients.http.HttpUrl

import com.bed.chat.external.clients.http.response.MessageResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

import com.bed.chat.external.clients.websocket.WebSocketClient
import com.bed.chat.external.clients.websocket.response.WebSocketResponse
import com.bed.chat.external.clients.websocket.request.WebSocketDataRequest

class RemoteMessageDatasourceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val webSocketClient: WebSocketClient
) : RemoteMessageDatasource {
    override fun observerDataWebSocket(): Flow<WebSocketResponse> =
        webSocketClient.observer()

    override suspend fun disconnectWebSocket() {
        webSocketClient.disconnect()
    }

    override suspend fun connectWebSocket(parameter: Int) {
        webSocketClient.connect(parameter)
    }

    override suspend fun sendMessage(parameter: WebSocketDataRequest) {
        webSocketClient.send(parameter)
    }

    override suspend fun getMessage(parameter: Pair<Int, PaginationRequest>): Result<PaginatedResponse<MessageResponse>> =
        httpClient.request<PaginatedResponse<MessageResponse>> {
            method = HttpMethod.Get
            url(configurePaginationParameter(HttpUrl.MESSAGES, parameter.second, parameter.first))
        }
}
