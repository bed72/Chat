package com.bed.chat.domain.models.output.chat

import com.bed.chat.domain.models.output.user.UserOutputModel

data class ChatOutputModel(
    val id: Int,
    val unreadCount: Int,
    val timestamp: String,
    val lastMessage: String?,
    val members: List<UserOutputModel>,
)
