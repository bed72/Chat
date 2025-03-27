package com.bed.chat.external.clients.websocket.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.external.clients.websocket.serializer.WebSocketDataSerializer

@Serializable(with = WebSocketDataSerializer::class)
data class WebSocketDataResponse(
    @SerialName("type")
    val type: String,

    @SerialName("data")
    val data: Any
)


