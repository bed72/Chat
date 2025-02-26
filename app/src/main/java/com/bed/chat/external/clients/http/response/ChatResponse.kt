package com.bed.chat.external.clients.http.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.ChatOutputModel

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
