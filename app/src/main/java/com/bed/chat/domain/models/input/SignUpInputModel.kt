package com.bed.chat.domain.models.input

data class SignUpInputModel(
    val picture: Int?,
    val email: String,
    val username: String,
    val password: String,
)
