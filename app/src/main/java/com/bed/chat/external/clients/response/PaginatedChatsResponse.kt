package com.bed.chat.external.clients.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.PaginatedChatsOutputModel

@Serializable
data class PaginatedChatsResponse(
    @SerialName("total")
    val total: Int,

    @SerialName("hasMore")
    val hasMore: Boolean,

    @SerialName("conversations")
    val conversations: List<ChatResponse>,
)

fun PaginatedChatsResponse.toModel(selfId: Int?) = PaginatedChatsOutputModel(
    total = total,
    hasMore = hasMore,
    conversations = conversations.map { it.toModel(selfId) }
)
