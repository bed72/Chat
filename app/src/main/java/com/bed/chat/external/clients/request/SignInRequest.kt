package com.bed.chat.external.clients.request

import com.bed.chat.domain.models.input.SignInInputModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String
)

fun SignInInputModel.toRequest() = SignInRequest(
    username = username,
    password = password
)
