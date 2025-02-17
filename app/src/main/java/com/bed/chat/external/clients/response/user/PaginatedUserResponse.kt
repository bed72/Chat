package com.bed.chat.external.clients.response.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.user.PaginatedUserOutputModel

@Serializable
data class PaginatedUserResponse(
    @SerialName("total")
    val total: Int,

    @SerialName("hasMore")
    val hasMore: Boolean,

    @SerialName("users")
    val users: List<UserResponse>,
)

fun PaginatedUserResponse.toModel() = PaginatedUserOutputModel(
    total = total,
    hasMore = hasMore,
    users = users.map { it.toModel(false) }
)
