package com.bed.chat.external.clients.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("url")
    val url: String,

    @SerialName("name")
    val name: String,

    @SerialName("type")
    val type: String,
)
