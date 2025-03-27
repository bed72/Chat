package com.bed.chat.external.clients.websocket.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebSocketDataRequest(
    @SerialName("type")
    val type: String,

    @SerialName("data")
    val data: WebSocketRequest
)

@Serializable
data class WebSocketRequest(
    @SerialName("text")
    val message: String,

    @SerialName("timestamp")
    val timestamp: Long,

    @SerialName("receiverId")
    val receiverId: Int,

    @SerialName("messageId")
    val messageId: String
)
