package com.bed.chat.external.clients.http.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.MessageOutputModel

import com.bed.chat.external.clients.database.entities.MessageEntity

@Serializable
data class MessageResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("senderId")
    val senderId: Int,

    @SerialName("receiverId")
    val receiverId: Int,

    @SerialName("isUnread")
    val isUnread: Boolean,

    @SerialName("text")
    val text: String,

    @SerialName("timestamp")
    val timestamp: Long,
)

fun MessageResponse.toModel() = MessageOutputModel(
    id = id,
    senderId = senderId,
    receiverId = receiverId,
    isUnread = isUnread,
    text = text,
    timestamp = timestamp.toTimestamp()
)

fun MessageResponse.toEntity() = MessageEntity(
    id = id,
    senderId = senderId,
    receiverId = receiverId,
    isUnread = isUnread,
    text = text,
    timestamp = timestamp
)
