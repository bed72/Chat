package com.bed.chat.external.clients.request

import com.bed.chat.domain.models.input.SignUpInputModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String,

    @SerialName("firstName")
    val name: String,

    @SerialName("profilePictureId")
    val picture: String?,
)

fun SignUpInputModel.toRequest() = SignUpRequest(
    name = name,
    username = username,
    password = password,
    picture = picture
)
