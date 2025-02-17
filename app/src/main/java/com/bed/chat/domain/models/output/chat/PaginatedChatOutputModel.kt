package com.bed.chat.domain.models.output.chat

data class PaginatedChatOutputModel(
    val total: Int,
    val hasMore: Boolean,
    val conversations: List<ChatOutputModel>,
)
