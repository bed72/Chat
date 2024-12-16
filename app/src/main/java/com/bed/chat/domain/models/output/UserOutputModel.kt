package com.bed.chat.domain.models.output

data class UserOutputModel(
    val id: Int,
    val username: String,
    val lastName: String,
    val firstName: String,
    val profilePicture: String?,
)
