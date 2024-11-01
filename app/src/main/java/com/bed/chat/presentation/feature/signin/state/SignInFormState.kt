package com.bed.chat.presentation.feature.signin.state

data class SignInFormState(
    val email: String = "",
    val emailMessage: String? = null,
    val emailIsValid: Boolean = false,

    val password: String = "",
    val passwordMessage: String? = null,
    val passwordIsValid: Boolean = false,

    val message: String? = null,

    val isLoading: Boolean = false,
    val formIsValid: Boolean = false,
)
