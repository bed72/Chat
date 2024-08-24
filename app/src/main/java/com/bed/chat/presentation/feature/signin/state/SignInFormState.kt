package com.bed.chat.presentation.feature.signin.state

data class SignInFormState(
    val email: String = "",
    val emailFailure: String? = null,

    val password: String = "",
    val passwordFailure: String? = null,

    val isLoading: Boolean = false,
)
