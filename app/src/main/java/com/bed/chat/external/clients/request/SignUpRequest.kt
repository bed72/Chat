package com.bed.chat.external.clients.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String,

    @SerialName("firstName")
    val firstName: String,

    @SerialName("profilePictureId")
    val picture: String?,
)
