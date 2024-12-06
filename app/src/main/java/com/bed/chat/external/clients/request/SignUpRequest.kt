package com.bed.chat.external.clients.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.input.SignUpInputModel

@Serializable
data class SignUpRequest(
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String,

    @SerialName("firstName")
    val name: String,

    @SerialName("lastName")
    val lastname: String,

    @SerialName("profilePictureId")
    val picture: Int?,
)

fun SignUpInputModel.toRequest() = SignUpRequest(
    name = email,
    picture = picture,
    username = username,
    lastname = username,
    password = password,
)
