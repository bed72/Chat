package com.bed.chat.presentation.feature.signin.state

data class SignInFormState(
    val username: String = "",
    val usernameMessage: String? = null,
    val usernameIsValid: Boolean = false,

    val password: String = "",
    val passwordMessage: String? = null,
    val passwordIsValid: Boolean = false,

    val messageSuccess: String? = null,
    val messageFailure: String? = null,

    val isLoading: Boolean = false,
    val formIsValid: Boolean = false,
)
