package com.bed.chat.external.clients.response.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.chat.PaginatedChatOutputModel

@Serializable
data class PaginatedChatResponse(
    @SerialName("total")
    val total: Int,

    @SerialName("hasMore")
    val hasMore: Boolean,

    @SerialName("conversations")
    val conversations: List<ChatResponse>,
)

fun PaginatedChatResponse.toModel(selfId: Int?) = PaginatedChatOutputModel(
    total = total,
    hasMore = hasMore,
    conversations = conversations.map { it.toModel(selfId) }
)
