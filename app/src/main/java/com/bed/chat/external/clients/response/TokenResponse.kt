package com.bed.chat.external.clients.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("token")
    val token: String,
)

