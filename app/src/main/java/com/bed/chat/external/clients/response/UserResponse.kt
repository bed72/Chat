package com.bed.chat.external.clients.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.UserOutputModel

@Serializable
data class UserResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("self")
    val self: Boolean,

    @SerialName("username")
    val username: String,

    @SerialName("lastName")
    val lastName: String,

    @SerialName("firstName")
    val firstName: String,

    @SerialName("profilePicture")
    val profilePicture: String?,
)

fun UserResponse.toModel(self: Boolean) = UserOutputModel(
    id = id,
    self = self,
    username = username,
    lastName = lastName,
    firstName = firstName,
    profilePicture = profilePicture
)
