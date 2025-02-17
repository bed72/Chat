package com.bed.chat.domain.models.output.user

data class UserOutputModel(
    val id: Int,
    val self: Boolean,
    val username: String,
    val lastName: String,
    val firstName: String,
    val profilePicture: String?,
)
