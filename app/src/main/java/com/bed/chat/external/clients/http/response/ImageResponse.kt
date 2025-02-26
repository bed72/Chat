package com.bed.chat.external.clients.http.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.ImageOutputModel

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

fun ImageResponse.toModel() = ImageOutputModel(
    id = id,
    url = url,
    name = name,
    type = type,
)
