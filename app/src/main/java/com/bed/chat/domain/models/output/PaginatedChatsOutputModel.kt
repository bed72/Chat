package com.bed.chat.domain.models.output

data class PaginatedChatsOutputModel(
    val total: Int,
    val hasMore: Boolean,
    val conversations: List<ChatOutputModel>,
)
