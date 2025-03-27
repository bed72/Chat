package com.bed.chat.external.clients.websocket.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActiveUserIdsResponse(
    @SerialName("activeUserIds")
    val activeUserIds: List<Int>
)
