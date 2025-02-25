package com.bed.chat.domain.models.output

data class MessageOutputModel(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val isUnread: Boolean,
    val text: String,
    val timestamp: String,
)
