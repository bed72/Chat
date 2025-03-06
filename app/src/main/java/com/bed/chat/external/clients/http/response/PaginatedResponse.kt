package com.bed.chat.external.clients.http.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import kotlinx.serialization.ExperimentalSerializationApi

import com.bed.chat.domain.models.output.PaginatedOutputModel

@Serializable
data class PaginatedResponse<out T> @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("total")
    val total: Int,

    @SerialName("hasMore")
    val hasMore: Boolean,

    @JsonNames("users", "messages", "conversations")
    val data: List<T>,
)

fun PaginatedResponse<ChatResponse>.toModel(selfId: Int?) =
    PaginatedOutputModel(
        total = total,
        hasMore = hasMore,
        data = data.map { it.toModel(selfId) }
    )
