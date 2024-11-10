package com.bed.chat.domain.models.input

data class SignUpInputModel(
    val email: String,
    val username: String,
    val password: String,
    val picture: String?,
)
