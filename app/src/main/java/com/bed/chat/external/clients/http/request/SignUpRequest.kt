package com.bed.chat.external.clients.http.request

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
    val firstName: String,

    @SerialName("lastName")
    val lastName: String,

    @SerialName("profilePictureId")
    val profilePictureId: Int?,
)

fun SignUpInputModel.toRequest() = SignUpRequest(
    username = email,
    lastName = username,
    password = password,
    firstName = username,
    profilePictureId = picture,
)
