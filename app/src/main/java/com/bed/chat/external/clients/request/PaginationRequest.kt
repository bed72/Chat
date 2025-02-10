package com.bed.chat.external.clients.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationRequest(
    @SerialName("limit")
    val limit: Int,

    @SerialName("offset")
    val offset: Int,
)
