package com.bed.chat.external.clients.http.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    @SerialName("token")
    val token: String
)
