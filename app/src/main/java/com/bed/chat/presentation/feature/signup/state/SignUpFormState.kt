package com.bed.chat.presentation.feature.signup.state

data class SignUpFormState(
    val firstName: String = "",
    val firstNameMessage: String? = null,
    val firstNameIsValid: Boolean = false,

    val secondName: String = "",
    val secondNameMessage: String? = null,
    val secondNameIsValid: Boolean = false,

    val email: String = "",
    val emailMessage: String? = null,
    val emailIsValid: Boolean = false,

    val password: String = "",
    val passwordMessage: String? = null,
    val passwordIsValid: Boolean = false,

    val isLoading: Boolean = false,
)
