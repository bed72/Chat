package com.bed.chat.domain.models.output

data class MessageWithMembersOutputModel(
    val ids: List<Int>?,
    val messages: MessageOutputModel?
)

data class MessageOutputModel(
    val id: Int?,
    val autoId: Int,
    val senderId: Int,
    val receiverId: Int,
    val message: String,
    val timestamp: String,
    val isSelf: Boolean,
    val isUnread: Boolean,
)
