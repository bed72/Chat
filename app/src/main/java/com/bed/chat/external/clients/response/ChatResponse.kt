package com.bed.chat.external.clients.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.ChatOutputModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable
data class ChatResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("unreadCount")
    val unreadCount: Int,

    @SerialName("lastMessage")
    val lastMessage: String?,

    @SerialName("members")
    val members: List<UserResponse>,

    @SerialName("createdAt")
    val createdAt: Long,

    @SerialName("updatedAt")
    val updatedAt: Long,
)

fun ChatResponse.toModel(selfId: Int?) = ChatOutputModel(
    id = id,
    unreadCount = unreadCount,
    lastMessage = lastMessage,
    timestamp = updatedAt.toTimestamp(),
    members = members.map { it.toModel(it.id == selfId) }
)

private fun Long.toTimestamp(): String {
    val now = LocalDateTime.now()
    val time = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    return if (now.toLocalDate() == time.toLocalDate()) time.format(DateTimeFormatter.ofPattern("HH:mm"))
        else time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
