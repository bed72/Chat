package com.bed.chat.domain.models

data class MessageOutputModel(
    val id: Int?,
    val autoId: Int,
    val senderId: Int,
    val receiverId: Int,
    val text: String,
    val date: String,
    val isSelf: Boolean,
    val isUnread: Boolean,
)
