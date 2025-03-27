package com.bed.chat.external.clients.websocket

import javax.inject.Inject

import kotlinx.coroutines.isActive
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.filterIsInstance

import io.ktor.websocket.close
import io.ktor.websocket.Frame
import io.ktor.websocket.readText

import io.ktor.client.request.url

import kotlinx.serialization.json.Json

import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession

import com.bed.chat.external.clients.http.HttpUrl
import com.bed.chat.external.clients.http.HttpClient
import com.bed.chat.external.clients.http.response.MessageResponse

import com.bed.chat.external.clients.websocket.response.WebSocketResponse
import com.bed.chat.external.clients.websocket.request.WebSocketDataRequest
import com.bed.chat.external.clients.websocket.response.WebSocketDataResponse

interface WebSocketClient {
    fun observer(): Flow<WebSocketResponse>

    suspend fun disconnect()
    suspend fun connect(parameter: Int): Result<Unit>
    suspend fun send(parameter: WebSocketDataRequest)
}

class WebSocketClientImpl @Inject constructor(
    private val client: HttpClient
) : WebSocketClient {
    private var session: DefaultClientWebSocketSession? = null

    override suspend fun disconnect() {
        session?.close()
        session = null
    }

    override suspend fun connect(parameter: Int): Result<Unit> {
        if (session != null) return Result.success(Unit)

        try {
            session = client.http.webSocketSession {
                    url("${HttpUrl.WS.value}/$parameter")
            }

            return Result.success(Unit)
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }

    @Suppress("RethrowCaughtException")
    override suspend fun send(parameter: WebSocketDataRequest) {
        if (session == null)
            throw WebSocketException("WebSocket is null or not active")

        try {
            session?.sendSerialized(parameter)
        } catch (exception: Exception) {
            throw exception
        }
    }

    override fun observer(): Flow<WebSocketResponse> =
        if (session == null) toFailure()
        else session!!
            .incoming
            .receiveAsFlow()
            .filterIsInstance(Frame.Text::class)
            .map { toResponse(it) }
            .catch { toFailure(it) }


    private fun toFailure(failure: Throwable? = null) =
        flowOf(WebSocketResponse.ConnectionFailure(failure ?: Throwable("WebSocket is null")))

    private fun toResponse(frame: Frame.Text): WebSocketResponse {
        val value = Json.decodeFromString<WebSocketDataResponse>(frame.readText())

        return when (val data = value.data) {
            is MessageResponse -> WebSocketResponse.MessageReceived(data)
            else -> WebSocketResponse.NotHandleYet
        }
    }
}
