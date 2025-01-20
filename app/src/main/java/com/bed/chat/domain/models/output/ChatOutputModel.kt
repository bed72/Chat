package com.bed.chat.domain.models.output

data class ChatOutputModel(
    val id: Int,
    val unreadCount: Int,
    val timestamp: String,
    val lastMessage: String?,
    val members: List<UserOutputModel>,
)
