package com.bed.chat.presentation.feature.signup.state

sealed interface SignUpFormEvent {
    data object Submit : SignUpFormEvent
    data class EmailChanged(val email: String) : SignUpFormEvent
    data class PasswordChanged(val password: String) : SignUpFormEvent
    data class FirstNameChanged(val firstName: String) : SignUpFormEvent
    data class SecondNameChanged(val secondName: String) : SignUpFormEvent
}
